/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.configuration;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.net.URI;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheFileConfiguration {

    private static final String DEFAULT_DIRECTORY_ROOT = String.join(File.separator, SystemUtils.getUserHome().getAbsolutePath(), ".cache", "dev_cache");

    @NotBlank
    private String directoryRoot;

    @Nonnull
    public URI getDirectoryRootUri() {
        return getDirectoryRootUri(null);
    }

    @Nonnull
    public URI getDirectoryRootUri(String defaultScheme) {

        final var uri = CacheConfigurationTools.ensureURIIsADirectory(URI.create(Optional.ofNullable(getDirectoryRoot()).orElse(DEFAULT_DIRECTORY_ROOT)));
        if (uri.getScheme() == null && defaultScheme != null) {
            return URI.create(String.format("%s:%s", defaultScheme, uri));
        } else {
            return uri;
        }
    }
}
