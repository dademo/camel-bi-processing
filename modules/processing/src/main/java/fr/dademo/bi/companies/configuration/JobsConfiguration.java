package fr.dademo.bi.companies.configuration;

import fr.dademo.bi.companies.configuration.exceptions.MissingJobConfigurationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobsConfiguration {

    @Nonnull
    private Map<String, JobConfiguration> jobs;

    public JobConfiguration getJobConfigurationByName(@Nonnull String jobName) {

        return Optional.ofNullable(jobs.get(jobName))
                .orElseThrow(MissingJobConfigurationException.ofJob(jobName));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobConfiguration {

        private boolean enabled;

        @Min(1)
        private int chunkSize = 100000;

        @Min(1)
        private int maxThreads = 8;
    }
}
