package fr.dademo.bi.companies.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.output.MigrateOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlywayMigrationTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlywayMigrationTools.class);

    public static void applyMigration(Flyway flyway) {

        final var migrationLocations = Arrays.stream(flyway.getConfiguration().getLocations())
                .map(Location::toString)
                .collect(Collectors.joining(", "));

        LOGGER.info("Applying migration using classpath `{}`", migrationLocations);

        final var migrateResult = flyway.migrate();

        migrateResult.warnings.forEach(FlywayMigrationTools::printMigrationWarning);
        migrateResult.migrations.forEach(FlywayMigrationTools::debugMigrateOutput);

        LOGGER.info("Applied `{}` migration from `{}` to `{}` on database `{}` on schema `{}`",
                migrateResult.migrationsExecuted,
                migrateResult.initialSchemaVersion,
                migrateResult.targetSchemaVersion,
                migrateResult.database,
                migrateResult.schemaName
        );
    }

    private static void printMigrationWarning(String warning) {
        LOGGER.warn("Warning: {}", warning);
    }

    private static void debugMigrateOutput(MigrateOutput migrateOutput) {

        LOGGER.debug("{}:{} : {}:{}, took {} milliseconds",
                migrateOutput.filepath,
                migrateOutput.type,
                migrateOutput.description,
                migrateOutput.version,
                migrateOutput.executionTime
        );
    }
}
