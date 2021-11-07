package fr.dademo.data.generic.stream_definitions.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.nio.file.Path;

@Configuration
@ConfigurationProperties(prefix = CacheConfiguration.CONFIGURATION_PREFIX)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheConfiguration {

    public static final String CONFIGURATION_PREFIX = "cache";

    private boolean enabled = true;

    @NotBlank
    private String directoryRoot = String.join(File.separator, SystemUtils.getUserHome().getAbsolutePath(), ".cache", "dev-http-cache");

    @Nonnull
    public Path getDirectoryRootPath() {
        return Path.of(getDirectoryRoot());
    }

}
