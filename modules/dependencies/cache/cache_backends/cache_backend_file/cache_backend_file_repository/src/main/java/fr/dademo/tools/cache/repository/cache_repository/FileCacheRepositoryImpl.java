/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.beans.CacheFileEnabledConditional;
import fr.dademo.tools.cache.configuration.CacheConfiguration;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.cache.repository.cache_index.CacheIndexRepository;
import fr.dademo.tools.cache.repository.exception.MissingCachedInputStreamException;
import fr.dademo.tools.cache.repository.support.CachedInputStreamWrapper;
import fr.dademo.tools.lock.repository.LockFactory;
import fr.dademo.tools.tools.HashTools;
import jakarta.annotation.Nonnull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.TeeInputStream;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Repository
@ConditionalOnBean(CacheFileEnabledConditional.class)
public class FileCacheRepositoryImpl<T extends InputStreamIdentifier<?>> extends FileCacheRepositoryBeanLifecycle<T> implements CacheRepository<T> {

    private static final String HASH_ALGORITHM = "SHA-512";
    private static final MessageDigest HASH_COMPUTER = HashTools.getHashComputerForAlgorithm(HASH_ALGORITHM);
    private static final String TEMP_PREFIX = normalizedName(FileCacheRepositoryImpl.class.getName());

    private final CacheIndexRepository<T> cacheIndexRepository;

    public FileCacheRepositoryImpl(@Nonnull LockFactory lockFactory,
                                   @Nonnull CacheConfiguration cacheConfiguration,
                                   @Nonnull CacheIndexRepository<T> cacheIndexRepository) {
        super(lockFactory, cacheConfiguration);
        this.cacheIndexRepository = cacheIndexRepository;
    }

    private static String normalizedName(String string) {
        return string.replace(File.separator, "_");
    }

    @Nonnull
    @Override
    public Optional<CachedInputStreamIdentifier<T>> getCachedInputStreamIdentifierOf(InputStreamIdentifier<?> inputStreamIdentifier) throws IOException {

        return cacheIndexRepository.readIndex().stream()
            .filter(cachedInputStreamIdentifier -> cachedInputStreamIdentifier.getCachedIdentifier().equals(inputStreamIdentifier))
            .findFirst();
    }

    @Nonnull
    @Override
    public InputStream readFromCachedInputStream(@Nonnull CachedInputStreamIdentifier<T> cachedInputStreamIdentifier) {

        log.debug("Will read from cache for file `{}`", cachedInputStreamIdentifier.getCachedIdentifier().getDescription());
        return openFileInputStream(
            getCachedFileFor(cachedInputStreamIdentifier)
        ).orElseThrow(() -> new MissingCachedInputStreamException(cachedInputStreamIdentifier.getCachedIdentifier()));
    }

    @Nonnull
    @Override
    @SuppressWarnings("java:S2095")
    public InputStream cacheInputStream(@Nonnull InputStream inputStream, @Nonnull T inputStreamIdentifier) throws IOException {

        log.debug("Storing input stream in cache for identifier `{}`", inputStreamIdentifier.getDescription());
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
                            log.debug("Cleaning working dir for identifier `{}`", inputStreamIdentifier.getDescription());
                            deleteFile(tempCachedFile);
                        }
                    }
                } else {
                    log.warn("Input stream is not valid and will not be persisted");
                }
            });
    }

    @SneakyThrows
    @Override
    public void deleteFromCache(@Nonnull T inputStreamIdentifier) throws IOException {

        log.info("Removing stream {}", inputStreamIdentifier);
        try (final var lock = acquireCacheLockForIdentifier(inputStreamIdentifier)) {

            // Updating index
            final var indexFileContent = cacheIndexRepository.readIndex();
            indexFileContent.remove(buildCachedInputStreamIdentifier(inputStreamIdentifier));
            cacheIndexRepository.persistIndex(indexFileContent);

            getCachedInputStreamIdentifierOf(inputStreamIdentifier)
                .map(this::getCachedFileFor)
                .ifPresent(this::deleteFile);
        }
        log.info("Removed file {}", "");
    }


    @SneakyThrows
    private synchronized void persistCache(File tempCachedFile, T inputFileIdentifier) {

        log.debug("Final persisting cached identifier `{}`", inputFileIdentifier.getDescription());
        final var cachedInputStreamIdentifier = buildCachedInputStreamIdentifier(inputFileIdentifier);

        try (final var lock = acquireCacheLockForIdentifier(inputFileIdentifier)) {
            final var indexFileContent = cacheIndexRepository.readIndex();
            indexFileContent.add(cachedInputStreamIdentifier);
            final var finalCachedFileFile = getCachedFileFor(cachedInputStreamIdentifier);

            try {
                log.debug("Moving cached file");
                FileUtils.moveFile(tempCachedFile, finalCachedFileFile);
                log.debug("Cached file moved");
                cacheIndexRepository.persistIndex(indexFileContent);
            } catch (IOException e) {
                // Cleaning
                if (tempCachedFile.exists()) {
                    log.debug("Removing cache file `{}`", tempCachedFile.getAbsolutePath());
                    deleteFile(tempCachedFile);
                }
                if (finalCachedFileFile.exists()) {
                    log.debug("Removing cache file `{}`", finalCachedFileFile.getAbsolutePath());
                    deleteFile(finalCachedFileFile);
                }
                throw new UncheckedIOException(e);
            }
        }
    }

    @SneakyThrows
    private void deleteFile(File file) {

        log.debug("Will delete file `{}`", file.getAbsolutePath());
        FileUtils.delete(file);
        log.debug("File `{}` deleted", file.getAbsolutePath());
    }

    @SneakyThrows
    private Optional<FileInputStream> openFileInputStream(File file) {

        try {
            return Optional.of(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            log.error("Cached file {} not found", file.getAbsolutePath());
            return Optional.empty();
        }
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
}
