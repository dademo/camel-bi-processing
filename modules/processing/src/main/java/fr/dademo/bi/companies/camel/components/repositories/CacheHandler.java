package fr.dademo.bi.companies.camel.components.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.type.CollectionType;
import fr.dademo.bi.companies.camel.components.repositories.entities.CachedFileDescription;
import fr.dademo.bi.companies.camel.components.repositories.entities.HashDefinition;
import fr.dademo.bi.companies.camel.components.repositories.exceptions.HashMismatchException;
import fr.dademo.bi.companies.camel.components.repositories.exceptions.MissingCachedFileException;
import fr.dademo.bi.companies.camel.components.repositories.exceptions.NotADirectoryException;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.TeeInputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static fr.dademo.bi.companies.tools.hash.HashTools.computeHash;
import static fr.dademo.bi.companies.tools.hash.HashTools.getHashComputerForAlgorithm;
import static org.jboss.logging.Logger.getLogger;

public class CacheHandler {

    private static final org.jboss.logging.Logger LOGGER = getLogger(CacheHandler.class);
    private static final String PREFIX = "bi-cache";
    private static final String LOCK_FILE_NAME = "index.lock";
    private static final String RESOURCES_DIRECTORY_NAME = "resources";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String HASH_ALGORITHM = "MD5";
    private static final MessageDigest HASH_COMPUTER = getHashComputerForAlgorithm(HASH_ALGORITHM);

    private final Path cacheDirectoryRoot;

    public CacheHandler(@Nonnull Path cacheDirectoryRoot) {
        this.cacheDirectoryRoot = cacheDirectoryRoot;

        // Checks
        ensureCacheDirectoryResourcesExists();
    }

    public static CollectionType collectionTypeDefinitionOfCachedFileDescription() {

        return MAPPER.getTypeFactory()
                .constructCollectionType(List.class, CachedFileDescription.class);
    }

    @SneakyThrows
    public boolean hasCachedInputStream(@Nonnull String inputFileIdentifier) {

        var cachedFileDescription = withLockedLockFile(
                () -> readLockFile()
                        .stream().filter(
                                e -> inputFileIdentifier.equals(e.getInputFileIdentifier())
                        )
                        .findFirst()
        );

        return cachedFileDescription
                .map(this::checkFileIdentifierValid)
                .orElse(false);
    }

    @SneakyThrows
    public void readFromCachedInputStream(@Nonnull String inputFileIdentifier,
                                          @Nonnull Consumer<InputStream> resultConsumer) {

        LOGGER.debug(String.format("Reading on local cache for identifier '%s'", inputFileIdentifier));
        Path cachedFilePath = withLockedLockFile(
                () -> Path.of(
                        cacheDirectoryRoot.toString(),
                        RESOURCES_DIRECTORY_NAME,
                        readLockFile().stream()
                                .filter(e -> inputFileIdentifier.equals(e.getInputFileIdentifier()))
                                .findFirst().map(CachedFileDescription::getFinalFileName)
                                .orElseThrow(() -> new MissingCachedFileException(inputFileIdentifier))
                ));

        try (var fileInputStream = new FileInputStream(cachedFilePath.toFile())) {
            resultConsumer.accept(fileInputStream);
        }
    }

    @SneakyThrows
    public void consumeAndCacheInputStream(@Nonnull InputStream inputStream,
                                           @Nonnull String inputFileIdentifier,
                                           @Nullable Duration expiration,
                                           @Nonnull List<HashDefinition> hashProvider,
                                           @Nonnull Consumer<InputStream> resultConsumer) {

        LOGGER.debug(String.format("Storing input stream in cache for identifier '%s'", inputFileIdentifier));
        // Writing body to a temp file
        var tempFilePath = Files.createTempFile(PREFIX, "");
        try (var cacheFile = new FileOutputStream(tempFilePath.toFile())) {
            resultConsumer.accept(new TeeInputStream(inputStream, cacheFile));
            cacheFile.flush();
        }

        hashProvider.forEach(hashDefinition -> validateFileHash(inputFileIdentifier, tempFilePath, hashDefinition));

        // Persisting cache
        persistCache(tempFilePath, inputFileIdentifier, expiration);
    }

    private List<CachedFileDescription> lockFileMapUsingDirectory() {

        return Files.exists(lockFilePathUsingCacheDirectoryRoot()) ?
                readLockFile() :
                new ArrayList<>();
    }

    private boolean checkFileIdentifierValid(CachedFileDescription cachedFileDescription) {

        if (!cachedFileDescription.isValid()) {
            deleteFromCache(cachedFileDescription);
            return false;
        } else {
            return true;
        }
    }

    private synchronized void deleteFromCache(CachedFileDescription cachedFileDescription) {

        LOGGER.debug(String.format("Removing cached file '%s'", cachedFileDescription.getFinalFileName()));
        withLockedLockFile(() -> {

            var lockFileFinalContent = readLockFile().stream()
                    .filter(e -> e.getFinalFileName().equals(cachedFileDescription.getFinalFileName()))
                    .collect(Collectors.toList());

            try {
                FileUtils.delete(
                        Path.of(
                                cacheDirectoryRoot.toString(),
                                RESOURCES_DIRECTORY_NAME,
                                cachedFileDescription.getFinalFileName()).toFile());

                persistLockFile(lockFileFinalContent);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    @SneakyThrows
    private void validateFileHash(String inputFileIdentifier, Path filePath, HashDefinition hashDefinition) {

        var computedDigest = computeHash(
                getHashComputerForAlgorithm(hashDefinition.getHashAlgorithm()),
                new FileInputStream(filePath.toFile())
        );
        var expectedHash = hashDefinition.getHash();

        if (!computedDigest.equals(expectedHash)) {
            throw new HashMismatchException(inputFileIdentifier, computedDigest, expectedHash);
        }
    }

    @SneakyThrows
    private synchronized void persistCache(Path tempCachedFile,
                                           String inputFileIdentifier,
                                           Duration expiration) {

        LOGGER.debug(String.format("Final persisting cached identifier '%s'", inputFileIdentifier));
        var finalFileName = DatatypeConverter
                .printHexBinary(HASH_COMPUTER.digest(inputFileIdentifier.getBytes()))
                .toUpperCase();

        withLockedLockFile(
                () -> {
                    var lockFileContent = lockFileMapUsingDirectory();
                    lockFileContent.add(CachedFileDescription.of(inputFileIdentifier, finalFileName, expiration));

                    try {
                        FileUtils.moveFile(
                                tempCachedFile.toFile(),
                                Path.of(cacheDirectoryRoot.toString(), RESOURCES_DIRECTORY_NAME, finalFileName).toFile()
                        );

                        persistLockFile(lockFileContent);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
        );
    }

    @SneakyThrows
    private void withLockedLockFile(Runnable onLockAcquired) {

        withLockedLockFile(() -> {
            onLockAcquired.run();
            return null;
        });
    }

    @SneakyThrows
    private <T> T withLockedLockFile(Supplier<T> onLockAcquired) {

        // Locking index file
        try (var fileChannel = FileChannel.open(
                lockFilePathUsingCacheDirectoryRoot(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            try (var lock = fileChannel.lock()) {
                return onLockAcquired.get();
            }
        }
    }

    @SneakyThrows
    private List<CachedFileDescription> readLockFile() {

        LOGGER.debug("Reading lock file");
        try {
            return MAPPER.readValue(
                    lockFilePathUsingCacheDirectoryRoot().toFile(),
                    collectionTypeDefinitionOfCachedFileDescription()
            );
        } catch (MismatchedInputException ex) {
            LOGGER.debug("Unable to read lock file");
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    private void persistLockFile(List<CachedFileDescription> lockFileContent) {

        LOGGER.debug("Writing lock file");
        MAPPER.writeValue(
                lockFilePathUsingCacheDirectoryRoot().toFile(),
                lockFileContent
        );
    }

    private Path lockFilePathUsingCacheDirectoryRoot() {
        return Path.of(cacheDirectoryRoot.toString(), LOCK_FILE_NAME);
    }

    private void ensureCacheDirectoryResourcesExists() {

        var cacheDirectoryResourcesName = Path.of(cacheDirectoryRoot.toString(), RESOURCES_DIRECTORY_NAME);
        if (!Files.exists(cacheDirectoryResourcesName)) {
            cacheDirectoryResourcesName.toFile().mkdirs();
        } else if (!Files.isDirectory(cacheDirectoryResourcesName)) {
            throw new NotADirectoryException(cacheDirectoryResourcesName.toString());
        }
    }

}
