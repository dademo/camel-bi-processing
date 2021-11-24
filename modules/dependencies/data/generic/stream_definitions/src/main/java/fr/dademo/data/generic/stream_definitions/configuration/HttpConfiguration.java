/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.generic.stream_definitions.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.nio.file.Path;

import static fr.dademo.data.generic.stream_definitions.configuration.HttpConfiguration.CONFIGURATION_PREFIX;

/**
 * @author dademo
 */
@Configuration
@ConfigurationProperties(prefix = CONFIGURATION_PREFIX)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpConfiguration {

    public static final String CONFIGURATION_PREFIX = "http";

    @Min(0)
    private long connectTimeoutSeconds = 0;

    @Min(0)
    private long callReadTimeoutSeconds = 0;

    @Min(0)
    private long callTimeoutSeconds = 0;

    @Nonnull
    private CacheConfiguration cacheConfiguration = new CacheConfiguration();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CacheConfiguration {

        private boolean enabled = true;

        @NotBlank
        private String directoryRoot = String.join(File.separator, SystemUtils.getUserHome().getAbsolutePath(), ".cache", "dev-http-cache");

        @Nonnull
        public Path getDirectoryRootPath() {
            return Path.of(getDirectoryRoot());
        }
    }
}
