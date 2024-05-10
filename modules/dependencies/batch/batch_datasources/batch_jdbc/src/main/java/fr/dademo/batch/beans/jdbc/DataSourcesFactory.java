/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.dademo.batch.beans.jdbc.tools.FlywayMigrationsSupplier;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.FlywayMigrationsConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.conf.ThrowExceptions;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Component
public class DataSourcesFactory {

    private final Map<String, DataSource> cachedDataSources = new HashMap<>();

    private final Map<String, DatabaseSQLDialectSupplier> cachedDataSourcesDialectProviders = new HashMap<>();

    private final BatchDataSourcesConfiguration batchDataSourcesConfiguration;
    private final BatchConfiguration batchConfiguration;
    private final FlywayMigrationsConfiguration flywayMigrationsConfiguration;

    public DataSourcesFactory(
        BatchDataSourcesConfiguration batchDataSourcesConfiguration,
        BatchConfiguration batchConfiguration,
        FlywayMigrationsConfiguration flywayMigrationsConfiguration
    ) {
        this.batchDataSourcesConfiguration = batchDataSourcesConfiguration;
        this.batchConfiguration = batchConfiguration;
        this.flywayMigrationsConfiguration = flywayMigrationsConfiguration;
    }

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

    public DSLContext getJobInputDslContextByName(@Nonnull String jobName) {

        final var jobInputDataSourceName = Optional.ofNullable(
            batchConfiguration
                .getJobConfigurationByName(jobName)
                .getInputDataSource()
        );

        return getDslContext(
            jobInputDataSourceName
                .map(BatchConfiguration.JobDataSourceConfiguration::getName)
                .orElseThrow(MissingJobDataSourceConfigurationException.forJob(jobName))
        );
    }

    public DSLContext getJobOutputDslContextByJobName(@Nonnull String jobName) {

        final var jobOutputDataSourceName = Optional.ofNullable(
            batchConfiguration
                .getJobConfigurationByName(jobName)
                .getInputDataSource()
        );

        return getDslContext(
            jobOutputDataSourceName
                .map(BatchConfiguration.JobDataSourceConfiguration::getName)
                .orElseThrow(MissingJobDataSourceConfigurationException.forJob(jobName))
        );
    }

    public DSLContext getDslContextByDataSourceName(@Nonnull String dataSourceName) {
        return getDslContext(dataSourceName);
    }

    @Nonnull
    private DSLContext getDslContext(@Nonnull String dataSourceName) {

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

        hikariConfig.setAutoCommit(true);
        hikariConfig.setJdbcUrl(configuration.getUrl());
        hikariConfig.setMinimumIdle(configuration.getMinimumIdle());
        hikariConfig.setMaximumPoolSize(configuration.getMaximumPoolSize());
        hikariConfig.setConnectionTimeout(configuration.getConnectionTimeoutMillis());
        hikariConfig.setIdleTimeout(configuration.getIdleTimeoutMillis());
        hikariConfig.setValidationTimeout(configuration.getValidationTimeoutMillis());
        Optional.ofNullable(configuration.getUsername()).ifPresent(hikariConfig::setUsername);
        Optional.ofNullable(configuration.getPassword()).ifPresent(hikariConfig::setPassword);
        Optional.ofNullable(configuration.getCatalog()).ifPresent(hikariConfig::setCatalog);
        Optional.ofNullable(configuration.getSchema()).ifPresent(hikariConfig::setSchema);
        Optional.ofNullable(configuration.getDriverClassName()).ifPresent(hikariConfig::setDriverClassName);


        if ("batch".equals(dataSourceName)) {
            applyBatchConfiguration(hikariConfig);
        }

        return new HikariDataSource(hikariConfig);
    }

    @Nonnull
    private DatabaseSQLDialectSupplier databaseSQLDialectProviderForDataSource(@Nonnull String dataSourceName) {
        return new DatabaseSQLDialectSupplier(getDataSource(dataSourceName));
    }

    private void applyBatchConfiguration(HikariConfig hikariConfig) {

        hikariConfig.setSchema(null);
        hikariConfig.setMinimumIdle(Math.max(hikariConfig.getMinimumIdle(), 5));
        hikariConfig.setMaximumPoolSize(Math.max(hikariConfig.getMinimumIdle(), 10));
        hikariConfig.setConnectionTimeout(0);
    }
}
