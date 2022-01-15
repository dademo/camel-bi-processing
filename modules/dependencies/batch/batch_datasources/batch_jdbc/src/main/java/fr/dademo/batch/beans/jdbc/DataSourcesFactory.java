/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.dademo.batch.beans.jdbc.tools.FlywayMigrationsSupplier;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.FlywayMigrationsConfiguration;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.conf.ThrowExceptions;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author dademo
 */
@Component
public class DataSourcesFactory {

    private final Map<String, DataSource> cachedDataSources = new HashMap<>();

    private final Map<String, DatabaseSQLDialectSupplier> cachedDataSourcesDialectProviders = new HashMap<>();

    @Autowired
    private BatchDataSourcesConfiguration batchDataSourcesConfiguration;

    @Autowired
    private FlywayMigrationsConfiguration flywayMigrationsConfiguration;

    @Nonnull
    public DataSource getDataSource(@NotEmpty String dataSourceName) {

        return cachedDataSources.computeIfAbsent(
            dataSourceName,
            this::hikariDataSourceForConfiguration
        );
    }

    public DatabaseSQLDialectSupplier getDatabaseSQLDialectProvider(@NotEmpty String dataSourceName) {

        return cachedDataSourcesDialectProviders.computeIfAbsent(
            dataSourceName,
            this::databaseSQLDialectProviderForDataSource
        );
    }

    public Flyway getMigration(@NotEmpty String dataSourceName) {

        return FlywayMigrationsSupplier.prepareFlywayMigration(
            dataSourceName,
            getDataSource(dataSourceName),
            getDatabaseSQLDialectProvider(dataSourceName).get(),
            flywayMigrationsConfiguration.getMigrationByDataSourceName(dataSourceName)
        );
    }

    public Stream<Flyway> getAllMigrations() {
        return batchDataSourcesConfiguration.getJdbc().keySet().stream().map(this::getMigration);
    }

    @Nonnull
    public DSLContext getDslContext(@Nonnull String dataSourceName) {

        return DSL.using(
            getDataSource(dataSourceName),
            getDatabaseSQLDialectProvider(dataSourceName).get(),
            new Settings()
                .withStatementType(StatementType.PREPARED_STATEMENT)
                .withAttachRecords(false)
                .withReturnRecordToPojo(true)
                .withExecuteLogging(false)
                .withThrowExceptions(ThrowExceptions.THROW_ALL)
                .withFetchWarnings(true)
        );
    }

    @Nonnull
    private DataSource hikariDataSourceForConfiguration(@Nonnull String dataSourceName) {

        final var configuration = batchDataSourcesConfiguration.getJDBCDataSourceConfigurationByName(dataSourceName);
        final var hikariConfig = new HikariConfig();

        hikariConfig.setAutoCommit(false);
        hikariConfig.setJdbcUrl(configuration.getUrl());
        hikariConfig.setMinimumIdle(configuration.getMinimumIdle());
        hikariConfig.setMaximumPoolSize(configuration.getMaximumPoolSize());
        Optional.ofNullable(configuration.getUsername()).ifPresent(hikariConfig::setUsername);
        Optional.ofNullable(configuration.getPassword()).ifPresent(hikariConfig::setPassword);
        Optional.ofNullable(configuration.getDriverClassName()).ifPresent(hikariConfig::setDriverClassName);
        hikariConfig.setConnectionTimeout(configuration.getConnectionTimeoutMS());

        return new HikariDataSource(hikariConfig);
    }

    @Nonnull
    private DatabaseSQLDialectSupplier databaseSQLDialectProviderForDataSource(@Nonnull String dataSourceName) {
        return new DatabaseSQLDialectSupplier(getDataSource(dataSourceName));
    }
}
