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
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.net.URI;

/**
 * @author dademo
 */
@Configuration
@ConfigurationProperties(prefix = CacheConfiguration.CONFIGURATION_PREFIX)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheConfiguration {

    public static final String CONFIGURATION_PREFIX = "cache";

    private boolean enabled = true;

    @NotBlank
    private String directoryRoot = String.join(File.separator, SystemUtils.getUserHome().getAbsolutePath(), ".cache", "dev_cache");

    @Nonnull
    public URI getDirectoryRootUri() {
        return getDirectoryRootUri(null);
    }

    @Nonnull
    public URI getDirectoryRootUri(String defaultScheme) {
        final var uri = URI.create(getDirectoryRoot());
        if (uri.getScheme() == null && defaultScheme != null) {
            return URI.create(String.format("%s:%s", defaultScheme, uri));
        } else {
            return uri;
        }
    }
}
