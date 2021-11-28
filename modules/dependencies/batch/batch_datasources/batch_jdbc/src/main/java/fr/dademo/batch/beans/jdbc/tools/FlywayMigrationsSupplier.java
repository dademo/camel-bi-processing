/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.jdbc.tools;

import fr.dademo.batch.beans.jdbc.tools.exception.MissingMigrationPathException;
import fr.dademo.batch.configuration.FlywayMigrationsConfiguration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.jooq.SQLDialect;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlywayMigrationsSupplier {

    public static final String LOCATION_SHARED = "shared";

    public static Flyway prepareFlywayMigration(@Nonnull String dataSourceName,
                                                @Nonnull DataSource dataSource,
                                                @Nonnull SQLDialect sqlDialect,
                                                @Nonnull FlywayMigrationsConfiguration.MigrationConfiguration migrationConfiguration) {

        return new FluentConfiguration()
            .dataSource(dataSource)
            .locations(
                Optional.ofNullable(migrationConfiguration.getLocations())
                    .map(migrationBasePath -> resolvedMigrationPaths(sqlDialect, migrationBasePath))
                    .orElseThrow(MissingMigrationPathException.ofDataSource(dataSourceName)))
            .defaultSchema(migrationConfiguration.getSchema())
            .createSchemas(
                Optional.ofNullable(migrationConfiguration.getCreateSchemas())
                    .orElse(FlywayMigrationsConfiguration.MigrationConfiguration.getDefaultCreateSchemas()))
            .cleanDisabled(
                Optional.ofNullable(migrationConfiguration.getCleanDisabled())
                    .orElse(FlywayMigrationsConfiguration.MigrationConfiguration.getDefaultCleanDisabled()))
            .baselineOnMigrate(
                Optional.ofNullable(migrationConfiguration.getBaselineOnMigrate())
                    .orElse(FlywayMigrationsConfiguration.MigrationConfiguration.getDefaultBaselineOnMigrate()))
            .target(MigrationVersion.LATEST)
            .load();
    }

    @Nonnull
    private static String[] resolvedMigrationPaths(@Nonnull SQLDialect sqlDialect, @Nonnull List<String> basePath) {

        return basePath.stream()
            .flatMap(location -> Stream.of(
                    Optional.of(LOCATION_SHARED),
                    Optional.ofNullable(mapSqlDialectToResource(sqlDialect)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(v -> location.concat("/").concat(v))
            )
            .toArray(String[]::new);
    }

    @Nullable
    private static String mapSqlDialectToResource(@Nonnull SQLDialect sqlDialect) {

        switch (sqlDialect) {
            case POSTGRES:
                return "postgresql";
            case MARIADB:
            case MYSQL:
                return "mysql";
            case SQLITE:
                return "sqlite";
            default:
                // We don't support any other kind of database
                return null;
        }
    }
}
