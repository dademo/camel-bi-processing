package fr.dademo.bi.companies.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.nio.file.Path;

@Configuration
@ConfigurationProperties(prefix = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpConfiguration {

    @Min(0)
    private long connectTimeoutSeconds;

    @Min(0)
    private long callReadTimeoutSeconds;

    @Min(0)
    private long callTimeoutSeconds;

    @Nullable
    private CacheConfiguration cacheConfiguration;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CacheConfiguration {

        private boolean enabled = true;

        @NotBlank
        private String directoryRoot = String.join(File.separator, SystemUtils.getUserHome().getAbsolutePath(), ".cache", "dev-http-cache");

        public Path getDirectoryRootPath() {
            return Path.of(getDirectoryRoot());
        }
    }
}
