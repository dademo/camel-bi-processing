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

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.integration.spring.SpringResourceAccessor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("unused")
@Slf4j
public class LiquibaseMigrationsSupplier {

    public static final String MIGRATIONS_BASE_PATH = "classpath:db/migration";

    @NotEmpty
    private final String migrationFolder;
    @NotEmpty
    private final String changeLogFileName;
    @Nullable
    private final String databaseSchema;
    @Nullable
    private final String databaseCatalog;
    @Nonnull
    private final String contexts;
    @Nonnull
    private final DataSource dataSource;
    @Nonnull
    private final ResourceLoader resourceLoader;

    @Builder
    public LiquibaseMigrationsSupplier(@NotEmpty String migrationFolder, @NotEmpty String changeLogFileName,
                                       @Nullable String databaseCatalog, @Nullable String databaseSchema,
                                       @Nonnull String contexts, @Nonnull DataSource dataSource,
                                       @Nonnull ResourceLoader resourceLoader) {

        this.migrationFolder = migrationFolder;
        this.changeLogFileName = changeLogFileName;
        this.databaseCatalog = databaseCatalog;
        this.databaseSchema = databaseSchema;
        this.contexts = contexts;
        this.dataSource = dataSource;
        this.resourceLoader = resourceLoader;
    }

    @SneakyThrows
    public void applyMigrations() {

        synchronized (dataSource) {
            try (final var connection = dataSource.getConnection()) {

                final var database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
                Optional.ofNullable(databaseSchema).ifPresent(safeApplyDatabaseOperation(database::setDefaultSchemaName));
                Optional.ofNullable(databaseCatalog).ifPresent(safeApplyDatabaseOperation(database::setDefaultCatalogName));

                try (final var liquibase = new Liquibase(getChangeLogFile(), new SpringResourceAccessor(resourceLoader), database)) {
                    final var springLiquibase = new SpringLiquibase();
                    springLiquibase.setShouldRun(true);
                    springLiquibase.setDataSource(dataSource);
                    springLiquibase.setChangeLog(getChangeLogFile());
                    springLiquibase.setContexts(contexts);
                    springLiquibase.afterPropertiesSet();
                }
            }
        }
    }

    private Consumer<String> safeApplyDatabaseOperation(DatabaseSetOperation operation) {

        return value -> {
            try {
                operation.accept(value);
            } catch (DatabaseException ex) {
                log.debug("Got exception, may be present due to incompatible backend ; will continue", ex);
            }
        };
    }

    private String getChangeLogFile() {

        return String.format("%s/%s/%s",
            MIGRATIONS_BASE_PATH,
            migrationFolder,
            changeLogFileName
        );
    }

    @FunctionalInterface
    private interface DatabaseSetOperation {

        void accept(String value) throws DatabaseException;
    }
}
