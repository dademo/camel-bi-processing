/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import jakarta.validation.constraints.Min;

/**
 * @author dademo
 */
@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "persistence")
public class PersistenceConfiguration {

    @Nonnull
    private DatasourceConfiguration datasource;

    private boolean cacheEnabled = false;

    @Min(0)
    private int batchSize = 0;

    @Min(0)
    private int minimumIdle = 0;

    @Min(1)
    private int maximumPoolSize = 1;

    @Data
    @NoArgsConstructor
    public static class DatasourceConfiguration {

        @Nonnull
        private String url;

        @Nonnull
        private String username;

        @Nonnull
        private String password;

        @Nullable
        private String schema = "public";
    }
}
