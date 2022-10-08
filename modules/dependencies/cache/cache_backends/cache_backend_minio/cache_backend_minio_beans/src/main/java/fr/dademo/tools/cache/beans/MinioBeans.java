/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.beans;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import fr.dademo.tools.cache.configuration.CacheMinioConfiguration;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.dademo.tools.cache.beans.CacheBeanConstants.*;

@Slf4j
@Configuration
@ConditionalOnBean(CacheMinioEnabledConditional.class)
public class MinioBeans {

    @SneakyThrows
    @Bean
    @ConditionalOnMissingBean(MinioClient.class)
    public MinioClient minioClient(CacheMinioConfiguration cacheMinioConfiguration,
                                   @Autowired(required = false) OkHttpClient okHttpClient) {

        final var minioClientBuilder = MinioClient.builder()
            .endpoint(cacheMinioConfiguration.getEndpoint())
            .credentials(cacheMinioConfiguration.getAccessKey(), cacheMinioConfiguration.getSecretKey());

        Optional.ofNullable(okHttpClient).ifPresent(minioClientBuilder::httpClient);

        final var minioClient = minioClientBuilder.build();
        final var tempBucketName = Optional.ofNullable(cacheMinioConfiguration.getTempBucketName())
            .filter(v -> !v.isEmpty())
            .orElse(DEFAULT_TEMP_BUCKET_NAME);
        final var bucketExists = BucketExistsArgs.builder()
            .bucket(cacheMinioConfiguration.getBucketName())
            .build();
        final var tempBucketExists = BucketExistsArgs.builder()
            .bucket(tempBucketName)
            .build();

        if (!minioClient.bucketExists(bucketExists) &&
            Boolean.TRUE.equals(cacheMinioConfiguration.getCreateBucket())) {
            log.info("Will crate MinIO bucket {}", cacheMinioConfiguration.getBucketName());
            minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(cacheMinioConfiguration.getBucketName())
                .objectLock(false)
                .build());
        }

        if (!minioClient.bucketExists(tempBucketExists) &&
            Boolean.TRUE.equals(cacheMinioConfiguration.getCreateBucket())) {
            log.info("Will crate MinIO temp bucket {}", tempBucketName);
            minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(tempBucketName)
                .objectLock(false)
                .build());
        }

        return minioClient;
    }

    @Bean(MINIO_STREAM_THREAD_POOL_BEAN)
    @ConditionalOnMissingBean(name = MINIO_STREAM_THREAD_POOL_BEAN)
    public ExecutorService minioStreamsThreadPool() {

        return Executors.newFixedThreadPool(
            MINIO_STREAM_THREAD_POOL_SIZE,
            new ThreadFactoryBuilder()
                .setNameFormat(MINIO_STREAM_THREADS_NAME_FORMAT)
                .build()
        );
    }
}
