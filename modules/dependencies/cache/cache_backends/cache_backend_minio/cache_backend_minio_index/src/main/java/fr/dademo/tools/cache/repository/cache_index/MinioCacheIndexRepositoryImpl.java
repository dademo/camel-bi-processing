/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_index;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.beans.CacheMinioEnabledConditional;
import fr.dademo.tools.cache.configuration.CacheMinioConfiguration;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.lock.repository.LockFactory;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.ErrorResponseException;
import jakarta.annotation.Nonnull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static fr.dademo.tools.cache.beans.CacheBeanConstants.MINIO_STREAM_THREAD_POOL_BEAN;

/**
 * @author dademo
 */
@Slf4j
@Repository
@ConditionalOnBean(CacheMinioEnabledConditional.class)
class MinioCacheIndexRepositoryImpl<T extends InputStreamIdentifier<?>> extends MinioCacheIndexRepositoryBeanLifecycle<T> implements CacheIndexRepository<T> {

    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    public static final String DIRECTORY_ROOT_URI_SCHEME = "file";

    private final ObjectMapper mapper;

    private final ExecutorService minioStreamsThreadPool;

    protected MinioCacheIndexRepositoryImpl(@Nonnull LockFactory lockFactory,
                                            @Nonnull CacheMinioConfiguration cacheMinioConfiguration,
                                            @Nonnull MinioClient minioClient,
                                            @Nonnull ObjectMapper mapper,
                                            @Nonnull @Qualifier(MINIO_STREAM_THREAD_POOL_BEAN) ExecutorService minioStreamsThreadPool) {
        super(lockFactory, cacheMinioConfiguration, minioClient);
        this.mapper = mapper;
        this.minioStreamsThreadPool = minioStreamsThreadPool;
    }

    @Override
    @SneakyThrows
    public List<CachedInputStreamIdentifier<T>> readIndex() {

        log.debug("Reading index file");
        try (final var lock = acquireLock()) {
            if (indexFileExists()) {
                try (final var indexFileContentStream = getIndexFileContent()) {

                    try {
                        return mapper.readValue(
                            indexFileContentStream,
                            collectionTypeDefinitionOfCachedInputStreamIdentifierDef()
                        );
                    } catch (JacksonException ex) {
                        log.warn("Unable to read index file");
                        log.warn("Will clean the cache directory");
                        cleanCacheResourceDirectory();
                        return new ArrayList<>();
                    }
                }
            } else {
                return new ArrayList<>();
            }
        }
    }

    @Override
    @SneakyThrows
    public void persistIndex(List<CachedInputStreamIdentifier<T>> indexContent) {

        log.debug("Writing index file");
        try (final var lock = acquireLock()) {
            try (final var outputStream = new PipedOutputStream(); final var inputStream = new PipedInputStream(outputStream)) {

                final var serializerRunner = minioStreamsThreadPool.submit(() -> writeSerializedIndexValue(mapper, outputStream, indexContent));
                final var persistenceRunner = minioStreamsThreadPool.submit(() -> persistIndexFile(inputStream));

                serializerRunner.get();
                persistenceRunner.get();
            }
        }
    }

    @SneakyThrows
    private void writeSerializedIndexValue(ObjectMapper mapper,
                                           OutputStream outputStream,
                                           List<CachedInputStreamIdentifier<T>> indexContent) {
        mapper.writeValue(outputStream, indexContent);
    }

    @SneakyThrows
    private boolean indexFileExists() {

        try {
            final var indexObjectStatistics = getMinioClient().statObject(
                StatObjectArgs.builder()
                    .bucket(getCacheMinioConfiguration().getBucketName())
                    .object(LOCK_FILE_NAME)
                    .build()
            );
            return indexObjectStatistics.size() > 0L;
        } catch (ErrorResponseException ex) {
            return false;
        }
    }

    @SneakyThrows
    private InputStream getIndexFileContent() {

        return getMinioClient().getObject(
            GetObjectArgs.builder()
                .bucket(getCacheMinioConfiguration().getBucketName())
                .object(LOCK_FILE_NAME)
                .build()
        );
    }

    @SneakyThrows
    private void persistIndexFile(InputStream inputStream) {

        getMinioClient().putObject(
            PutObjectArgs.builder()
                .bucket(getCacheMinioConfiguration().getBucketName())
                .object(LOCK_FILE_NAME)
                .stream(inputStream, -1L, 64L * 1024 * 1024)    // 64 MB
                .contentType(CONTENT_TYPE_APPLICATION_JSON)
                .build()
        );
    }

    private CollectionType collectionTypeDefinitionOfCachedInputStreamIdentifierDef() {

        return mapper.getTypeFactory()
            .constructCollectionType(List.class, CachedInputStreamIdentifier.class);
    }
}
