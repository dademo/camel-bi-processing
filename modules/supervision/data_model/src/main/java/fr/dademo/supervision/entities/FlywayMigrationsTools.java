/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlywayMigrationsTools {

    public static Flyway prepareFlywayMigration(@Nonnull DataSource dataSource) {

        return new FluentConfiguration()
            .dataSource(dataSource)
            .createSchemas(false)
            .cleanDisabled(true)
            .baselineOnMigrate(true)
            .target(MigrationVersion.LATEST)
            .load();
    }
}
