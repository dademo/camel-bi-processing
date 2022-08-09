/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration;

import fr.dademo.batch.configuration.exception.MissingJobConfigurationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author dademo
 */
@Configuration
@ConfigurationProperties(prefix = "batch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchConfiguration {

    @Nonnull
    private Map<String, JobConfiguration> jobs = new HashMap<>();

    @Nonnull
    private BatchRepositoryConfiguration repository = new BatchRepositoryConfiguration();

    @Nullable
    private Integer executorThreadPoolSize;


    public JobConfiguration getJobConfigurationByName(@Nonnull String jobName) {

        return Optional.ofNullable(jobs.get(jobName))
            .orElseThrow(MissingJobConfigurationException.ofJob(jobName));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BatchRepositoryConfiguration {

        private boolean enabled = false;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobConfiguration {

        public static final boolean DEFAULT_IS_ENABLED = false;
        public static final int DEFAULT_CHUNK_SIZE = 10_000;

        @Nullable
        private Boolean enabled;

        @Nullable
        @Min(1)
        private Integer chunkSize;

        @Nullable
        @Min(1)
        private Integer maxThreads;

        public static boolean getDefaultIsEnabled() {
            return DEFAULT_IS_ENABLED;
        }

        public static int getDefaultChunkSize() {
            return DEFAULT_CHUNK_SIZE;
        }

        public static int getDefaultMaxThreads() {
            return Runtime.getRuntime().availableProcessors();
        }
    }
}
