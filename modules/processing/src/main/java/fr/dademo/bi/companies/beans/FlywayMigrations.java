package fr.dademo.bi.companies.beans;

import fr.dademo.bi.companies.beans.exception.MissingMigrationPathException;
import fr.dademo.bi.companies.configuration.FlywayMigrationsConfiguration;
import fr.dademo.bi.companies.tools.DatabaseSQLDialectProvider;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.jooq.SQLDialect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.dademo.bi.companies.beans.BeanValues.*;

@Configuration
public class FlywayMigrations {

    private static final String[][] STATIC_MIGRATION_PATHS_DEFINITION = new String[][]{
            {"batch", "classpath:db/migration/batch"},
            {"stg", "classpath:db/migration/stg"},
    };
    private static final Map<String, String> STATIC_MIGRATION_PATHS = Stream.of(STATIC_MIGRATION_PATHS_DEFINITION)
            .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));

    @Bean(FLYWAY_BATCH_BEAN_NAME)
    @ConditionalOnProperty(
            value = CONFIG_FLYWAY_MIGRATIONS + "." + BATCH_DATASOURCE_NAME + "." + CONFIG_ENABLED,
            havingValue = "true"
    )
    public Flyway batchFlywayMigration(@Qualifier(BATCH_DATASOURCE_BEAN_NAME) DataSource dataSource,
                                       @Qualifier(BATCH_DATASOURCE_DIALECT_PROVIDER_BEAN_NAME) DatabaseSQLDialectProvider databaseSQLDialectProvider,
                                       FlywayMigrationsConfiguration flywayMigrationsConfiguration) {
        return prepareMigration(
                BATCH_DATASOURCE_NAME,
                dataSource,
                databaseSQLDialectProvider.get(),
                flywayMigrationsConfiguration.getMigrationByDataSourceName(BATCH_DATASOURCE_NAME)
        );
    }

    @Bean(FLYWAY_BATCH_BEAN_NAME)
    @ConditionalOnProperty(
            value = CONFIG_FLYWAY_MIGRATIONS + "." + STG_DATASOURCE_NAME + "." + CONFIG_ENABLED,
            havingValue = "true"
    )
    public Flyway stgFlywayMigration(@Qualifier(STG_DATASOURCE_BEAN_NAME) DataSource dataSource,
                                     @Qualifier(STG_DATASOURCE_DIALECT_PROVIDER_BEAN_NAME) DatabaseSQLDialectProvider databaseSQLDialectProvider,
                                     FlywayMigrationsConfiguration flywayMigrationsConfiguration) {
        return prepareMigration(
                STG_DATASOURCE_NAME,
                dataSource,
                databaseSQLDialectProvider.get(),
                flywayMigrationsConfiguration.getMigrationByDataSourceName(STG_DATASOURCE_NAME)
        );
    }

    private Flyway prepareMigration(String dataSourceName,
                                    DataSource dataSource,
                                    SQLDialect sqlDialect,
                                    FlywayMigrationsConfiguration.MigrationConfiguration migrationConfiguration) {

        return new FluentConfiguration()
                .dataSource(dataSource)
                .locations(
                        Optional.ofNullable(STATIC_MIGRATION_PATHS.get(dataSourceName))
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

    private String[] resolvedMigrationPaths(SQLDialect sqlDialect, String basePath) {

        return Stream.of(
                        Optional.of("shared"),
                        Optional.ofNullable(mapSqlDialectToResource(sqlDialect))
                ).filter(Optional::isPresent)
                .map(Optional::get)
                .map(v -> basePath.concat("/").concat(v))
                .toArray(String[]::new);
    }

    @Nullable
    private String mapSqlDialectToResource(SQLDialect sqlDialect) {
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
