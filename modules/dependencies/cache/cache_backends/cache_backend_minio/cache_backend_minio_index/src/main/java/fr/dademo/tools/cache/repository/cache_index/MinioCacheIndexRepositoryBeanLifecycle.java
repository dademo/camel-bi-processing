/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_index;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.configuration.CacheMinioConfiguration;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;

/**
 * @author dademo
 */
@Slf4j
@NoArgsConstructor
public abstract class MinioCacheIndexRepositoryBeanLifecycle<T extends InputStreamIdentifier<?>> extends BaseCacheIndexRepository<T> {

    public static final String LOCK_FILE_NAME = "index.lock";


    @Getter
    @Autowired
    private CacheMinioConfiguration cacheMinioConfiguration;

    @Getter
    @Autowired
    private MinioClient minioClient;

    @SuppressWarnings("java:S3864")
    @SneakyThrows
    protected void cleanCacheResourceDirectory() {

        final var deleted = new AtomicInteger(0);

        StreamSupport.stream(
                minioClient.listObjects(
                    ListObjectsArgs.builder()
                        .bucket(cacheMinioConfiguration.getBucketName())
                        .recursive(true)
                        .build()
                ).spliterator(),
                false
            )
            .map(this::getItem)
            .filter(item -> !item.isDir())
            .peek(item -> deleted.incrementAndGet())
            .forEach(this::removeMinioObject);

        if (deleted.intValue() > 0) {
            log.info("Removed cache bucket {} and all its contents", cacheMinioConfiguration.getBucketName());
        }
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
}
