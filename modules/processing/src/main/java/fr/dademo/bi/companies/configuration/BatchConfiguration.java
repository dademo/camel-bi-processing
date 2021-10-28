package fr.dademo.bi.companies.configuration;

import fr.dademo.bi.companies.configuration.exceptions.MissingJobConfigurationException;
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

@Configuration
@ConfigurationProperties(prefix = "batch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchConfiguration {

    @Nonnull
    private Map<String, JobConfiguration> jobs = new HashMap<>();


    public JobConfiguration getJobConfigurationByName(@Nonnull String jobName) {

        return Optional.ofNullable(jobs.get(jobName))
                .orElseThrow(MissingJobConfigurationException.ofJob(jobName));
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
