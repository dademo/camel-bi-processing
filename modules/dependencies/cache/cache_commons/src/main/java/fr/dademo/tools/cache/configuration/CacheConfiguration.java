/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.configuration;

import jakarta.annotation.Nullable;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

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
    public static final String CONFIG_CACHE_BACKEND = "cacheBackend";

    private boolean enabled = true;

    private CacheBackend cacheBackend;

    @Nullable
    private CacheFileConfiguration file;

    public CacheFileConfiguration getFile() {

        return Optional.ofNullable(file)
            .orElseGet(() -> new CacheFileConfiguration(null));
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum CacheBackend {
        FILE("FILE"),
        VFS("VFS"),
        JDBC("JDBC"),
        MINIO("MINIO");

        public static final String CACHE_BACKEND_FILE = "file";
        public static final String CACHE_BACKEND_VFS = "vfs";
        public static final String CACHE_BACKEND_JDBC = "jdbc";
        public static final String CACHE_BACKEND_MINIO = "minio";
        private final String value;
    }
}
