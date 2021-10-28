package fr.dademo.bi.companies.beans;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.dademo.bi.companies.configuration.DataSourcesConfiguration;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import static fr.dademo.bi.companies.beans.BeanValues.*;

@Configuration
public class DataSources {

    @Autowired
    private DataSourcesConfiguration dataSourcesConfiguration;

    @Bean(STG_DATASOURCE_BEAN_NAME)
    @ConditionalOnProperty(
            value = CONFIG_DATASOURCE_JDBC + "." + STG_DATASOURCE_NAME + "." + CONFIG_ENABLED,
            havingValue = "true"
    )
    public DataSource stgDataSource() {
        return dataSourceForConfiguration(STG_DATASOURCE_NAME);
    }

    @Bean(STG_MONGO_CLIENT_CONFIG_BEAN_NAME)
    @ConditionalOnProperty(
            value = CONFIG_DATASOURCE_MONGODB + "." + STG_DATASOURCE_NAME + "." + CONFIG_ENABLED,
            havingValue = "true"
    )
    public MongoClient stgMongoClient() {
        return MongoClients.create(mongoClientSettingsForConfiguration(STG_DATASOURCE_NAME));
    }

    @Bean(STG_MONGO_TEMPLATE_CONFIG_BEAN_NAME)
    @ConditionalOnProperty(
            value = CONFIG_DATASOURCE_MONGODB + "." + STG_DATASOURCE_NAME + "." + CONFIG_ENABLED,
            havingValue = "true"
    )
    public MongoTemplate stgMongoTemplate(@Autowired MongoClient mongoClient) {

        return new MongoTemplate(
                mongoClient,
                dataSourcesConfiguration
                        .getMongoDBClientConfigurationByName(STG_DATASOURCE_NAME)
                        .getDatabase()
        );
    }

    private DataSource dataSourceForConfiguration(@Nonnull String configurationName) {

        final var config = dataSourcesConfiguration.getJDBCDataSourceConfigurationByName(configurationName);
        final var hikariConfig = new HikariConfig();

        hikariConfig.setAutoCommit(false);
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setConnectionTimeout(config.getConnectionTimeoutMS());

        return new HikariDataSource(hikariConfig);
    }

    private MongoClientSettings mongoClientSettingsForConfiguration(@Nonnull String configurationName) {
        return mongoClientSettingsForConfiguration(DEFAULT_SPRING_APPLICATION_NAME, configurationName);
    }

    private MongoClientSettings mongoClientSettingsForConfiguration(@Nonnull String applicationName, @Nonnull String configurationName) {

        final var configuration = dataSourcesConfiguration.getMongoDBClientConfigurationByName(configurationName);
        final var mongoClientSettingsBuilder = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(configuration.getConnectionString()))
                .applicationName(applicationName)
                .writeConcern(configuration.getWriteConcern())
                .readConcern(configuration.getReadConcern());

        if (Strings.isNotBlank(configuration.getUsername()) &&
                Strings.isNotBlank(configuration.getDatabase()) &&
                Strings.isNotBlank(configuration.getPassword())) {
            mongoClientSettingsBuilder.credential(MongoCredential.createCredential(configuration.getUsername(), configuration.getDatabase(), configuration.getPassword().toCharArray()));
        }

        return mongoClientSettingsBuilder.build();
    }
}
