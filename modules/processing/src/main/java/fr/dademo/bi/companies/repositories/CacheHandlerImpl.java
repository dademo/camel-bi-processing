package fr.dademo.bi.companies.repositories;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import fr.dademo.bi.companies.configuration.HttpConfiguration;
import fr.dademo.bi.companies.repositories.datamodel.HashDefinition;
import fr.dademo.bi.companies.repositories.exception.HashMismatchException;
import fr.dademo.bi.companies.repositories.exception.MissingCachedFileException;
import fr.dademo.bi.companies.repositories.exception.NotADirectoryException;
import fr.dademo.bi.companies.repositories.file.identifier.FileIdentifier;
import fr.dademo.bi.companies.repositories.file.validators.CachedFileValidator;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.TeeInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.dademo.bi.companies.tools.hash.HashTools.computeHash;
import static fr.dademo.bi.companies.tools.hash.HashTools.getHashComputerForAlgorithm;

@Repository
public class CacheHandlerImpl implements CacheHandler, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheHandlerImpl.class);
    private static final String PREFIX = "bi-cache";
    private static final String LOCK_FILE_NAME = "index.lock";
    private static final String RESOURCES_DIRECTORY_NAME = "resources";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String HASH_ALGORITHM = "MD5";
    private static final MessageDigest HASH_COMPUTER = getHashComputerForAlgorithm(HASH_ALGORITHM);

    @Autowired
    private HttpConfiguration httpConfiguration;

    @Autowired
    private ApplicationContext applicationContext;

    private static CollectionType collectionTypeDefinitionOfFileIdentifier() {

        return MAPPER.getTypeFactory()
                .constructCollectionType(List.class, FileIdentifier.class);
    }

    private Path getDirectoryRootPath() {

        return httpConfiguration
                .getCacheConfiguration()
                .getDirectoryRootPath();
    }

    @Override
    public void afterPropertiesSet() {

        // Checks
        ensureCacheDirectoryResourcesExists();
    }

    @Override
    @SneakyThrows
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean hasCachedInputStream(@Nonnull FileIdentifier<?> fileIdentifier) {

        final var foundFileIdentifier = withLockedLockFile(
                () -> readLockFile()
                        .stream().filter(fileIdentifier::equals)
                        .findFirst()
        );

        return foundFileIdentifier
                .map(fi -> checkFileIdentifierValid((FileIdentifier) fi))
                .orElse(false);
    }

    @Override
    @SneakyThrows
    public InputStream readFromCachedInputStream(@Nonnull FileIdentifier<?> fileIdentifier) {

        LOGGER.debug("Reading on local cache for identifier `{}`", fileIdentifier.getBaseUrl());
        Path cachedFilePath = withLockedLockFile(
                () -> Path.of(
                        getDirectoryRootPath().toString(),
                        RESOURCES_DIRECTORY_NAME,
                        readLockFile().stream()
                                .filter(fileIdentifier::equals)
                                .findFirst().map(FileIdentifier::getFinalFileName)
                                .orElseThrow(() -> new MissingCachedFileException(fileIdentifier))
                ));

        return new FileInputStream(cachedFilePath.toFile());
    }

    @Override
    @SuppressWarnings("java:S2095")
    @SneakyThrows
    public InputStream cacheInputStream(@Nonnull InputStream inputStream,
                                        @Nonnull FileIdentifier<?> fileIdentifier,
                                        @Nonnull List<HashDefinition> hashProvider) {

        LOGGER.debug("Storing input stream in cache for identifier `{}`", fileIdentifier.getBaseUrl());
        // Writing body to a temp file
        final var tempFilePath = Files.createTempFile(PREFIX, "");
        final var cachedFile = new FileOutputStream(tempFilePath.toFile());
        return new CachedInputStreamWrapper(
                new TeeInputStream(inputStream, cachedFile),
                () -> {
                    try {
                        cachedFile.close();
                        // Validating file hashes
                        hashProvider.forEach(hashDefinition -> validateFileHash(fileIdentifier, tempFilePath, hashDefinition));
                        // Persisting cache
                        persistCache(tempFilePath, fileIdentifier);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    } finally {
                        LOGGER.debug("Cleaning working dir for identifier `{}`", fileIdentifier.getBaseUrl());
                        cleanTempFile(tempFilePath, fileIdentifier);
                    }
                });
    }

    @SneakyThrows
    private void cleanTempFile(Path tempFilePath, FileIdentifier<?> inputFileIdentifier) {

        LOGGER.debug("Removing file `{}` for identifier `{}` if existing", tempFilePath, inputFileIdentifier.getBaseUrl());
        final var deleted = Files.deleteIfExists(tempFilePath);
        LOGGER.debug("File '{}' for identifier `{}` {} deleted", tempFilePath, inputFileIdentifier.getBaseUrl(), deleted ? "" : "not");
    }

    private List<FileIdentifier<?>> lockFileMapUsingDirectory() {

        return Files.exists(lockFilePathUsingCacheDirectoryRoot()) ?
                readLockFile() :
                new ArrayList<>();
    }

    private <T extends FileIdentifier<T>> boolean checkFileIdentifierValid(T fileIdentifier) {

        for (final var validator : fileIdentifier.getValidators()) {
            // We inject required beans
            validator.getBeansByName().forEach(beanName -> injectValidatorBeanByName(beanName, validator));
            validator.getBeansByClass().forEach(beanClass -> injectValidatorBeanByClass(beanClass, validator));

            if (!validator.isValid(fileIdentifier)) {
                deleteFromCache(fileIdentifier);
                return false;
            }
        }
        return true;
    }

    private void injectValidatorBeanByName(String beanName, CachedFileValidator<?> validator) {
        validator.onBeanAcquired(beanName, applicationContext.getBean(beanName));
    }

    private void injectValidatorBeanByClass(Class<?> beanClass, CachedFileValidator<?> validator) {
        validator.onBeanAcquired(beanClass, applicationContext.getBean(beanClass));
    }

    private synchronized void deleteFromCache(FileIdentifier<?> fileIdentifier) {

        LOGGER.debug("Removing cached file `{}`", fileIdentifier.getFinalFileName());
        withLockedLockFile(() -> {

            final var lockFileFinalContent = readLockFile().stream()
                    .filter(e -> Objects.equals(e.getFinalFileName(), fileIdentifier.getFinalFileName()))
                    .collect(Collectors.toList());

            try {
                FileUtils.delete(
                        Path.of(
                                getDirectoryRootPath().toString(),
                                RESOURCES_DIRECTORY_NAME,
                                fileIdentifier.getFinalFileName()).toFile());

                persistLockFile(lockFileFinalContent);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    @SneakyThrows
    private void validateFileHash(FileIdentifier<?> inputFileIdentifier, Path filePath, HashDefinition hashDefinition) {

        final var computedDigest = computeHash(
                getHashComputerForAlgorithm(hashDefinition.getHashAlgorithm()),
                new FileInputStream(filePath.toFile())
        ).toUpperCase();
        final var expectedHash = hashDefinition.getHash().toUpperCase();

        if (!computedDigest.equals(expectedHash)) {
            throw new HashMismatchException(inputFileIdentifier, expectedHash, computedDigest);
        }
    }

    @SneakyThrows
    private synchronized void persistCache(Path tempCachedFile, FileIdentifier<?> inputFileIdentifier) {

        LOGGER.debug("Final persisting cached identifier `{}`", inputFileIdentifier);
        final var finalFileName = DatatypeConverter
                .printHexBinary(HASH_COMPUTER.digest(inputFileIdentifier.getBaseUrl().toString().getBytes()))
                .toUpperCase();

        withLockedLockFile(
                () -> {
                    final var lockFileContent = lockFileMapUsingDirectory();
                    inputFileIdentifier.setFinalFileName(finalFileName);
                    lockFileContent.add(inputFileIdentifier);

                    try {
                        FileUtils.moveFile(
                                tempCachedFile.toFile(),
                                Path.of(getDirectoryRootPath().toString(), RESOURCES_DIRECTORY_NAME, finalFileName).toFile()
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
    private List<FileIdentifier<?>> readLockFile() {

        LOGGER.debug("Reading lock file");
        final var lockFile = lockFilePathUsingCacheDirectoryRoot().toFile();

        if (lockFile.exists() && lockFile.length() > 0) {
            try {
                return MAPPER.readValue(
                        lockFile,
                        collectionTypeDefinitionOfFileIdentifier()
                );
            } catch (JacksonException ex) {
                LOGGER.warn("Unable to read lock file");
                LOGGER.warn("Will clean the cache directory");
                final var cacheDir = Path.of(getDirectoryRootPath().toString(), RESOURCES_DIRECTORY_NAME).toFile();
                Optional.ofNullable(cacheDir.listFiles())
                        .stream()
                        .flatMap(Stream::of)
                        .forEach(this::deleteFile);
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    private void deleteFile(File file) {

        LOGGER.debug("Will delete file `{}`", file.getAbsolutePath());
        FileUtils.delete(file);
        LOGGER.debug("File `{}` deleted", file.getAbsolutePath());
    }

    @SneakyThrows
    private void persistLockFile(List<FileIdentifier<?>> lockFileContent) {

        LOGGER.debug("Writing lock file");
        MAPPER.writeValue(
                lockFilePathUsingCacheDirectoryRoot().toFile(),
                lockFileContent
        );
    }

    private Path lockFilePathUsingCacheDirectoryRoot() {
        return Path.of(getDirectoryRootPath().toString(), LOCK_FILE_NAME);
    }

    private void ensureCacheDirectoryResourcesExists() {

        final var cacheDirectoryResourcesName = Path.of(getDirectoryRootPath().toString(), RESOURCES_DIRECTORY_NAME);
        if (!Files.exists(cacheDirectoryResourcesName)) {
            cacheDirectoryResourcesName.toFile().mkdirs();
        } else if (!Files.isDirectory(cacheDirectoryResourcesName)) {
            throw new NotADirectoryException(cacheDirectoryResourcesName.toString());
        }
    }
}
