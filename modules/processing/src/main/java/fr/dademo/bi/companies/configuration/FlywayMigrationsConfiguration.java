/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author dademo
 */
@Configuration
@ConfigurationProperties(prefix = "flyway")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlywayMigrationsConfiguration {

    @Nonnull
    private Map<String, MigrationConfiguration> migrations = new HashMap<>();


    public MigrationConfiguration getMigrationByDataSourceName(@Nonnull String dataSourceName) {

        return Optional.ofNullable(migrations.get(dataSourceName))
            .orElse(MigrationConfiguration.DEFAULT_MIGRATION_CONFIGURATION);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MigrationConfiguration {

        public static final boolean DEFAULT_IS_ENABLED = false;
        public static final boolean DEFAULT_CREATE_SCHEMAS = false;
        public static final boolean DEFAULT_CLEAN_DISABLED = true;
        public static final boolean DEFAULT_BASELINE_ON_MIGRATE = true;
        private static final MigrationConfiguration DEFAULT_MIGRATION_CONFIGURATION;

        static {
            DEFAULT_MIGRATION_CONFIGURATION = MigrationConfiguration.builder()
                .enabled(getDefaultIsEnabled())
                .createSchemas(getDefaultCreateSchemas())
                .cleanDisabled(getDefaultCleanDisabled())
                .baselineOnMigrate(getDefaultBaselineOnMigrate())
                .build();
        }

        @Nullable
        private Boolean enabled;
        @Nullable
        private String schema;
        @Nullable
        private Boolean createSchemas;
        @Nullable
        private Boolean cleanDisabled;
        @Nullable
        private Boolean baselineOnMigrate;

        public static boolean getDefaultIsEnabled() {
            return DEFAULT_IS_ENABLED;
        }

        public static boolean getDefaultCreateSchemas() {
            return DEFAULT_CREATE_SCHEMAS;
        }

        public static boolean getDefaultCleanDisabled() {
            return DEFAULT_CLEAN_DISABLED;
        }

        public static boolean getDefaultBaselineOnMigrate() {
            return DEFAULT_BASELINE_ON_MIGRATE;
        }
    }
}
