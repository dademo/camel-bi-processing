package fr.dademo.bi.companies.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.type.CollectionType;
import fr.dademo.bi.companies.repositories.datamodel.CachedFileDescription;
import fr.dademo.bi.companies.repositories.datamodel.HashDefinition;
import fr.dademo.bi.companies.repositories.exceptions.HashMismatchException;
import fr.dademo.bi.companies.repositories.exceptions.MissingCachedFileException;
import fr.dademo.bi.companies.repositories.exceptions.NotADirectoryException;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.TeeInputStream;
import org.jboss.logging.Logger;

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
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static fr.dademo.bi.companies.tools.hash.HashTools.computeHash;
import static fr.dademo.bi.companies.tools.hash.HashTools.getHashComputerForAlgorithm;

public class CacheHandlerImpl implements CacheHandler {

    private static final org.jboss.logging.Logger LOGGER = Logger.getLogger(CacheHandlerImpl.class);
    private static final String PREFIX = "bi-cache";
    private static final String LOCK_FILE_NAME = "index.lock";
    private static final String RESOURCES_DIRECTORY_NAME = "resources";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String HASH_ALGORITHM = "MD5";
    private static final MessageDigest HASH_COMPUTER = getHashComputerForAlgorithm(HASH_ALGORITHM);

    private final Path cacheDirectoryRoot;

    public CacheHandlerImpl(@Nonnull Path cacheDirectoryRoot) {
        this.cacheDirectoryRoot = cacheDirectoryRoot;

        // Checks
        ensureCacheDirectoryResourcesExists();
    }

    private static CollectionType collectionTypeDefinitionOfCachedFileDescription() {

        return MAPPER.getTypeFactory()
                .constructCollectionType(List.class, CachedFileDescription.class);
    }

    @Override
    @SneakyThrows
    public boolean hasCachedInputStream(@Nonnull String inputFileIdentifier) {

        final var cachedFileDescription = withLockedLockFile(
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

    @Override
    @SneakyThrows
    public InputStream readFromCachedInputStream(@Nonnull String inputFileIdentifier) {

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

        return new FileInputStream(cachedFilePath.toFile());
    }

    @Override
    @SuppressWarnings("java:S2095")
    @SneakyThrows
    public InputStream cacheInputStream(@Nonnull InputStream inputStream,
                                        @Nonnull String inputFileIdentifier,
                                        @Nullable Duration expiration,
                                        @Nonnull List<HashDefinition> hashProvider) {

        LOGGER.debug(String.format("Storing input stream in cache for identifier '%s'", inputFileIdentifier));
        // Writing body to a temp file
        final var tempFilePath = Files.createTempFile(PREFIX, "");
        final var cachedFile = new FileOutputStream(tempFilePath.toFile());
        return new CachedInputStreamWrapper(
                new TeeInputStream(inputStream, cachedFile),
                () -> {
                    try {
                        cachedFile.close();
                        // Validating file hashes
                        hashProvider.forEach(hashDefinition -> validateFileHash(inputFileIdentifier, tempFilePath, hashDefinition));
                        // Persisting cache
                        persistCache(tempFilePath, inputFileIdentifier, expiration);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    } finally {
                        LOGGER.debug(String.format("Cleaning working dir for identifier '%s'", inputFileIdentifier));
                        cleanTempFile(tempFilePath, inputFileIdentifier);
                    }
                });
    }

    @SneakyThrows
    private void cleanTempFile(Path tempFilePath, String inputFileIdentifier) {

        LOGGER.debug(String.format("Removing file '%s' for identifier '%s'", tempFilePath, inputFileIdentifier));
        final var deleted = Files.deleteIfExists(tempFilePath);
        LOGGER.debug(String.format("File '%s' for identifier '%s' %s deleted", tempFilePath, inputFileIdentifier, deleted ? "" : "not"));
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

            final var lockFileFinalContent = readLockFile().stream()
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

        final var computedDigest = computeHash(
                getHashComputerForAlgorithm(hashDefinition.getHashAlgorithm()),
                new FileInputStream(filePath.toFile())
        );
        final var expectedHash = hashDefinition.getHash();

        if (!computedDigest.equals(expectedHash)) {
            throw new HashMismatchException(inputFileIdentifier, expectedHash, computedDigest);
        }
    }

    @SneakyThrows
    private synchronized void persistCache(Path tempCachedFile,
                                           String inputFileIdentifier,
                                           Duration expiration) {

        LOGGER.debug(String.format("Final persisting cached identifier '%s'", inputFileIdentifier));
        final var finalFileName = DatatypeConverter
                .printHexBinary(HASH_COMPUTER.digest(inputFileIdentifier.getBytes()))
                .toUpperCase();

        withLockedLockFile(
                () -> {
                    final var lockFileContent = lockFileMapUsingDirectory();
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
    private synchronized <T> T withLockedLockFile(Supplier<T> onLockAcquired) {

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

        final var cacheDirectoryResourcesName = Path.of(cacheDirectoryRoot.toString(), RESOURCES_DIRECTORY_NAME);
        if (!Files.exists(cacheDirectoryResourcesName)) {
            cacheDirectoryResourcesName.toFile().mkdirs();
        } else if (!Files.isDirectory(cacheDirectoryResourcesName)) {
            throw new NotADirectoryException(cacheDirectoryResourcesName.toString());
        }
    }

}
