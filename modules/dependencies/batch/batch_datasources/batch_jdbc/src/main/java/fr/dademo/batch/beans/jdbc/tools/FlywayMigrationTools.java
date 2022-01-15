/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.jdbc.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.output.MigrateOutput;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dademo
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlywayMigrationTools {

    public static void applyMigration(Flyway flyway) {

        final var migrationLocations = Arrays.stream(flyway.getConfiguration().getLocations())
            .map(Location::toString)
            .collect(Collectors.joining(", "));

        log.info("Applying migration using classpath `{}`", migrationLocations);

        final var migrateResult = flyway.migrate();

        migrateResult.warnings.forEach(FlywayMigrationTools::printMigrationWarning);
        migrateResult.migrations.forEach(FlywayMigrationTools::debugMigrateOutput);

        log.info("Applied `{}` migration from `{}` to `{}` on database `{}` on schema `{}`",
            migrateResult.migrationsExecuted,
            migrateResult.initialSchemaVersion,
            migrateResult.targetSchemaVersion,
            migrateResult.database,
            migrateResult.schemaName
        );
    }

    private static void printMigrationWarning(String warning) {
        log.warn("Warning: {}", warning);
    }

    private static void debugMigrateOutput(MigrateOutput migrateOutput) {

        log.debug("{}:{} : {}:{}, took {} milliseconds",
            migrateOutput.filepath,
            migrateOutput.type,
            migrateOutput.description,
            migrateOutput.version,
            migrateOutput.executionTime
        );
    }
}
