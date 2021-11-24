package fr.dademo.tools.cache.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.configuration.CacheConfiguration;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.cache.repository.exception.MissingCachedInputStreamException;
import fr.dademo.tools.cache.repository.support.CachedInputStreamWrapper;
import fr.dademo.tools.tools.HashTools;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.TeeInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Optional;

@ConditionalOnProperty(
        value = CacheConfiguration.CONFIGURATION_PREFIX + ".enabled",
        havingValue = "true"
)
@Repository
public class FileCacheRepositoryImpl<T extends InputStreamIdentifier<?>> extends FileCacheRepositoryBase implements CacheRepository<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCacheRepositoryImpl.class);
    private static final String HASH_ALGORITHM = "SHA-512";
    private static final MessageDigest HASH_COMPUTER = HashTools.getHashComputerForAlgorithm(HASH_ALGORITHM);
    private static final String TEMP_PREFIX = normalizedName(FileCacheRepositoryImpl.class.getName());

    @Autowired
    private CacheLockRepository<T> cacheLockRepository;

    private static String normalizedName(String string) {
        return string.replace(File.separator, "_");
    }

    @Nonnull
    @Override
    public Optional<CachedInputStreamIdentifier<T>> getCachedInputStreamIdentifierOf(InputStreamIdentifier<?> inputStreamIdentifier) {
        return cacheLockRepository.readLockFile().stream()
                .filter(cachedInputStreamIdentifier -> cachedInputStreamIdentifier.getCachedIdentifier().equals(inputStreamIdentifier))
                .findFirst();
    }

    @Override
    public boolean hasCachedInputStream(@Nonnull T fileIdentifier) {
        return getCachedInputStreamIdentifierOf(fileIdentifier).isPresent();
    }

    @Nonnull
    @Override
    public InputStream readFromCachedInputStream(@Nonnull T fileIdentifier) {

        LOGGER.debug("Will read from cache for file `{}`", fileIdentifier.getDescription());
        return getCachedInputStreamIdentifierOf(fileIdentifier)
                .map(this::getCachedFileOf)
                .map(this::openFileInputStream)
                .orElseThrow(() -> new MissingCachedInputStreamException(fileIdentifier));
    }

    @Nonnull
    @Override
    @SuppressWarnings("java:S2095")
    public InputStream cacheInputStream(@Nonnull InputStream inputStream, @Nonnull T inputStreamIdentifier) throws IOException {

        LOGGER.debug("Storing input stream in cache for identifier `{}`", inputStreamIdentifier.getDescription());
        // Writing body to a temp file
        final var tempCachedFile = Files.createTempFile(TEMP_PREFIX, "").toFile();
        final var cachedFile = new FileOutputStream(tempCachedFile);

        return CachedInputStreamWrapper.of(
                new TeeInputStream(inputStream, cachedFile, true),
                isValidInputStream -> {
                    if (isValidInputStream) {
                        try {
                            persistCache(tempCachedFile, inputStreamIdentifier);
                        } finally {
                            // In case of an error, we still clean the directories
                            if (tempCachedFile.exists()) {
                                LOGGER.debug("Cleaning working dir for identifier `{}`", inputStreamIdentifier.getDescription());
                                deleteFile(tempCachedFile);
                            }
                        }
                    } else {
                        LOGGER.warn("Input stream is not valid and will not be persistedq");
                    }
                });
    }

    @Override
    public void deleteFromCache(@Nonnull T inputStreamIdentifier) {

        LOGGER.info("Removing stream {}", inputStreamIdentifier);
        getCachedInputStreamIdentifierOf(inputStreamIdentifier)
                .map(this::getCachedFileOf)
                .ifPresent(this::deleteFile);
        LOGGER.info("Removed file {}", "");
    }


    private synchronized void persistCache(File tempCachedFile, T inputFileIdentifier) {

        LOGGER.debug("Final persisting cached identifier `{}`", inputFileIdentifier.getDescription());
        final var cachedInputStreamIdentifier = buildCachedInputStreamIdentifier(inputFileIdentifier);

        cacheLockRepository.withLockedLockFile(
                () -> {
                    final var lockFileContent = cacheLockRepository.readLockFile();
                    lockFileContent.add(cachedInputStreamIdentifier);
                    final var finalCachedFileFile = getCacheConfiguration().getDirectoryRootPath()
                            .resolve(RESOURCES_DIRECTORY_NAME)
                            .resolve(cachedInputStreamIdentifier.getFileName())
                            .toFile();

                    try {
                        LOGGER.debug("Moving cached file");
                        FileUtils.moveFile(tempCachedFile, finalCachedFileFile);
                        LOGGER.debug("Cached file moved");
                        cacheLockRepository.persistLockFile(lockFileContent);
                    } catch (IOException e) {
                        // Cleaning
                        if (tempCachedFile.exists()) {
                            LOGGER.debug("Removing cache file `{}`", tempCachedFile.getAbsolutePath());
                            deleteFile(tempCachedFile);
                        }
                        if (finalCachedFileFile.exists()) {
                            LOGGER.debug("Removing cache file `{}`", finalCachedFileFile.getAbsolutePath());
                            deleteFile(finalCachedFileFile);
                        }
                        throw new UncheckedIOException(e);
                    }
                }
        );
    }

    @SneakyThrows
    private void deleteFile(File file) {

        LOGGER.debug("Will delete file `{}`", file.getAbsolutePath());
        FileUtils.delete(file);
        LOGGER.debug("File `{}` deleted", file.getAbsolutePath());
    }

    @SneakyThrows
    private FileInputStream openFileInputStream(File file) {
        return new FileInputStream(file);
    }


    private CachedInputStreamIdentifier<T> buildCachedInputStreamIdentifier(T inputStreamIdentifier) {

        return CachedInputStreamIdentifier.<T>builder()
                .timestamp(LocalDateTime.now())
                .cachedIdentifier(inputStreamIdentifier)
                .fileName(getCachedFinalFileName(inputStreamIdentifier))
                .build();
    }

    private String getCachedFinalFileName(T inputStreamIdentifier) {
        return HashTools.computeHashString(HASH_COMPUTER, inputStreamIdentifier.getDescription().getBytes());
    }

    private File getCachedFileOf(CachedInputStreamIdentifier<?> cachedInputStreamIdentifier) {

        return getCacheConfiguration().getDirectoryRootPath()
                .resolve(RESOURCES_DIRECTORY_NAME)
                .resolve(cachedInputStreamIdentifier.getFileName())
                .toFile();
    }
}
