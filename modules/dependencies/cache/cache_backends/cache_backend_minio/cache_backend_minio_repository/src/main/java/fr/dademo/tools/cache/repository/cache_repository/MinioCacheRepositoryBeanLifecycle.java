/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.configuration.CacheMinioConfiguration;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import io.minio.*;
import io.minio.messages.Item;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;

import static fr.dademo.tools.cache.beans.CacheBeanConstants.DEFAULT_TEMP_BUCKET_NAME;

@Slf4j
@Getter
public abstract class MinioCacheRepositoryBeanLifecycle<T extends InputStreamIdentifier<?>> extends BaseCacheRepository<T> implements DisposableBean {

    public static final String TEMP_DIRECTORY_PREFIX = "TEMP_";
    public static final int TEMP_DIRECTORY_RANDOM_LENGTH = 16;
    public static final int TEMP_FILE_RANDOM_LENGTH = 32;
    protected static final String RESOURCES_DIRECTORY_NAME = "resources";

    private static final String TEMP_PREFIX = normalizedName(MinioCacheRepositoryImpl.class.getName());
    private static final Random random = new Random();

    private String tempDirectoryName = null;

    @Autowired
    private CacheMinioConfiguration cacheMinioConfiguration;

    @Autowired
    private MinioClient minioClient;


    private static String normalizedName(String string) {
        return string.replace(File.separator, "_");
    }

    @Override
    public void destroy() throws Exception {

        // Removing the temporary folder
        Optional.ofNullable(tempDirectoryName)
            .map(this::deleteTempDirectory)
            .ifPresent(deleted -> log.info("Removed temp directory {} of bucket {}", tempDirectoryName, cacheMinioConfiguration.getBucketName()));
    }

    @SneakyThrows
    protected ObjectWriteResponse writeTempFile(@Nonnull InputStream inputStream, @Nullable String contentType) {

        final var builder = PutObjectArgs.builder()
            .bucket(getTempBucketName())
            .object(createRandomObjectName())
            .stream(inputStream, -1L, 64L * 1024 * 1024);   // 64 MB

        Optional.ofNullable(contentType).ifPresent(builder::contentType);

        return minioClient.putObject(builder.build());
    }

    @SneakyThrows
    protected InputStream getCachedInputStreamFor(CachedInputStreamIdentifier<T> cachedInputStreamIdentifier) {

        return minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(getCacheMinioConfiguration().getBucketName())
                .object(getObjectPathFor(cachedInputStreamIdentifier))
                .build()
        );
    }

    @SuppressWarnings("java:S3864")
    @SneakyThrows
    protected int deleteTempDirectory(String tempDirectoryName) {

        final var deleted = new AtomicInteger(0);

        StreamSupport.stream(
                minioClient.listObjects(
                    ListObjectsArgs.builder()
                        .bucket(getTempBucketName())
                        .recursive(true)
                        .build()
                ).spliterator(),
                false)
            .map(this::getItem)
            .filter(item -> !item.isDir())
            .filter(item -> item.objectName().startsWith(tempDirectoryName))
            .peek(item -> deleted.incrementAndGet())
            .forEach(this::removeMinioObject);

        return deleted.intValue();
    }

    protected synchronized String getTempDirectoryName() {

        if (tempDirectoryName == null) {
            tempDirectoryName = TEMP_DIRECTORY_PREFIX + getBase64Random(TEMP_DIRECTORY_RANDOM_LENGTH);
        }
        return tempDirectoryName;
    }

    protected String getObjectPathFor(CachedInputStreamIdentifier<T> cachedInputStreamIdentifier) {
        return String.format("%s/%s", RESOURCES_DIRECTORY_NAME, cachedInputStreamIdentifier.getFileName());
    }

    private String getTempBucketName() {

        return Optional.ofNullable(cacheMinioConfiguration.getTempBucketName())
            .filter(v -> !v.isEmpty())
            .orElse(DEFAULT_TEMP_BUCKET_NAME);
    }

    @SneakyThrows
    private Item getItem(Result<Item> item) {
        return item.get();
    }

    @SneakyThrows
    private void removeMinioObject(Item item) {

        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(cacheMinioConfiguration.getBucketName())
                .object(item.objectName())
                .build()
        );
    }

    private String createRandomObjectName() {
        return String.format("%s/%s", getTempDirectoryName(), getBase64Random(TEMP_FILE_RANDOM_LENGTH));
    }

    private String getBase64Random(int length) {

        final var bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().encodeToString(bytes);
    }
}
