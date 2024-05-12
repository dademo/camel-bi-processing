/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.beans.CacheMinioEnabledConditional;
import fr.dademo.tools.cache.configuration.CacheMinioConfiguration;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.cache.repository.cache_index.CacheIndexRepository;
import fr.dademo.tools.cache.repository.exception.MissingCachedInputStreamException;
import fr.dademo.tools.cache.repository.support.CachedInputStreamWrapper;
import fr.dademo.tools.lock.repository.LockFactory;
import fr.dademo.tools.tools.HashTools;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import jakarta.annotation.Nonnull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.TeeInputStream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static fr.dademo.tools.cache.beans.CacheBeanConstants.MINIO_STREAM_THREAD_POOL_BEAN;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Repository
@ConditionalOnBean(CacheMinioEnabledConditional.class)
public class MinioCacheRepositoryImpl<T extends InputStreamIdentifier<?>> extends MinioCacheRepositoryBeanLifecycle<T> implements CacheRepository<T> {

    private static final String HASH_ALGORITHM = "SHA-512";
    private static final MessageDigest HASH_COMPUTER = HashTools.getHashComputerForAlgorithm(HASH_ALGORITHM);

    private final CacheIndexRepository<T> cacheIndexRepository;

    private final ExecutorService minioStreamsThreadPool;

    public MinioCacheRepositoryImpl(@Nonnull LockFactory lockFactory,
                                    @Nonnull CacheMinioConfiguration cacheMinioConfiguration,
                                    @Nonnull MinioClient minioClient,
                                    @Nonnull CacheIndexRepository<T> cacheIndexRepository,
                                    @Nonnull @Qualifier(MINIO_STREAM_THREAD_POOL_BEAN) ExecutorService minioStreamsThreadPool) {
        super(lockFactory, cacheMinioConfiguration, minioClient);
        this.cacheIndexRepository = cacheIndexRepository;
        this.minioStreamsThreadPool = minioStreamsThreadPool;
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
        return Optional.ofNullable(
                getCachedInputStreamFor(cachedInputStreamIdentifier)
            )
            .orElseThrow(() -> new MissingCachedInputStreamException(cachedInputStreamIdentifier.getCachedIdentifier()));
    }

    @Nonnull
    @Override
    @SuppressWarnings({"java:S2095", "java:S112"})
    public InputStream cacheInputStream(@Nonnull InputStream inputStream, @Nonnull T inputStreamIdentifier) throws IOException {

        log.debug("Storing input stream in cache for identifier `{}`", inputStreamIdentifier.getDescription());

        // Writing body to a temp file
        final var pipedOutputStream = new PipedOutputStream();
        final var pipedInputStream = new PipedInputStream(pipedOutputStream);

        final var writerFuture = minioStreamsThreadPool.submit(() -> writeTempFile(pipedInputStream, inputStreamIdentifier.getContentType()));

        return CachedInputStreamWrapper.of(
            new TeeInputStream(inputStream, pipedOutputStream, true),
            isValidInputStream -> {
                try {
                    final var writtenTempFile = writerFuture.get();
                    if (isValidInputStream) {
                        try {
                            persistCache(writtenTempFile, inputStreamIdentifier);
                        } finally {
                            // In case of an error, we still clean the directories
                            if (fileExists(writtenTempFile.object())) {
                                log.debug("Cleaning working dir for identifier `{}`", inputStreamIdentifier.getDescription());
                                deleteFile(writtenTempFile.object());
                            }
                        }
                    } else {
                        log.warn("Input stream is not valid and will not be persisted");
                    }
                } catch (InterruptedException ex) {
                    // java:S2142
                    // Restore interrupted state...
                    Thread.currentThread().interrupt();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
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
                .map(this::getObjectPathFor)
                .ifPresent(this::deleteFile);
        }
    }


    @SneakyThrows
    private synchronized void persistCache(ObjectWriteResponse tempCachedFileName, T inputFileIdentifier) {

        log.debug("Final persisting cached identifier `{}`", inputFileIdentifier.getDescription());
        final var cachedInputStreamIdentifier = buildCachedInputStreamIdentifier(inputFileIdentifier);

        try (final var lock = acquireCacheLockForIdentifier(inputFileIdentifier)) {

            final var indexFileContent = cacheIndexRepository.readIndex();
            indexFileContent.add(cachedInputStreamIdentifier);

            try {
                log.debug("Moving cached file");
                getMinioClient().copyObject(
                    CopyObjectArgs.builder()
                        .bucket(getCacheMinioConfiguration().getBucketName())
                        .object(getObjectPathFor(cachedInputStreamIdentifier))
                        .source(CopySource.builder()
                            .bucket(tempCachedFileName.bucket())
                            .object(tempCachedFileName.object())
                            .build())
                        .build()
                );
                getMinioClient().removeObject(
                    RemoveObjectArgs.builder()
                        .bucket(tempCachedFileName.bucket())
                        .object(tempCachedFileName.object())
                        .build()
                );
                log.debug("Cached file moved");
                cacheIndexRepository.persistIndex(indexFileContent);
            } catch (IOException ex) {
                // Cleaning
                log.debug("Removed {} temp files", deleteTempDirectory(getTempDirectoryName()));
                throw new UncheckedIOException(ex);
            }
        }
    }

    @SneakyThrows
    private boolean fileExists(String fileName) {

        try {
            final var indexObjectStatistics = getMinioClient().statObject(
                StatObjectArgs.builder()
                    .bucket(getCacheMinioConfiguration().getBucketName())
                    .object(fileName)
                    .build()
            );
            return indexObjectStatistics.size() > 0L;
        } catch (ErrorResponseException ex) {
            return false;
        }
    }

    @SneakyThrows
    private void deleteFile(String fileName) {

        log.debug("Will delete file `{}`", fileName);
        getMinioClient().removeObject(
            RemoveObjectArgs.builder()
                .bucket(getCacheMinioConfiguration().getBucketName())
                .object(fileName)
                .build()
        );
        log.debug("File `{}` deleted", fileName);
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
