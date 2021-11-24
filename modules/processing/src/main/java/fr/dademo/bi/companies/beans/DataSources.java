/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

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
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.util.Optional;

import static fr.dademo.bi.companies.beans.BeanValues.*;

/**
 * @author dademo
 */
@Configuration
public class DataSources {

    @Autowired
    private DataSourcesConfiguration dataSourcesConfiguration;

    @Bean(BATCH_DATASOURCE_BEAN_NAME)
    @ConditionalOnProperty(
        value = CONFIG_DATASOURCE_JDBC + "." + BATCH_DATASOURCE_NAME + "." + CONFIG_ENABLED,
        havingValue = "true"
    )
    public DataSource batchDataSource() {
        return hikariDataSourceForConfiguration(BATCH_DATASOURCE_NAME);
    }

    @Bean(STG_DATASOURCE_BEAN_NAME)
    @ConditionalOnProperty(
        value = CONFIG_DATASOURCE_JDBC + "." + STG_DATASOURCE_NAME + "." + CONFIG_ENABLED,
        havingValue = "true"
    )
    public DataSource stgDataSource() {
        return hikariDataSourceForConfiguration(STG_DATASOURCE_NAME);
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
    public MongoTemplate stgMongoTemplate(MongoClient mongoClient) {

        return new MongoTemplate(
            mongoClient,
            dataSourcesConfiguration
                .getMongoDBClientConfigurationByName(STG_DATASOURCE_NAME)
                .getDatabase()
        );
    }

    private DataSource hikariDataSourceForConfiguration(@Nonnull String configurationName) {

        final var configuration = dataSourcesConfiguration.getJDBCDataSourceConfigurationByName(configurationName);
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

    private MongoClientSettings mongoClientSettingsForConfiguration(@Nonnull String configurationName) {
        return mongoClientSettingsForConfiguration(DEFAULT_SPRING_APPLICATION_NAME, configurationName);
    }

    private MongoClientSettings mongoClientSettingsForConfiguration(@Nonnull String applicationName, @Nonnull String configurationName) {

        final var configuration = dataSourcesConfiguration.getMongoDBClientConfigurationByName(configurationName);
        final var mongoClientSettingsBuilder = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(configuration.getConnectionString()))
            .applicationName(applicationName)
            .writeConcern(configuration.getWriteConcern())
            .readConcern(configuration.getReadConcern())
            .codecRegistry(CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
            ));

        if (Strings.isNotBlank(configuration.getUsername()) &&
            Strings.isNotBlank(configuration.getDatabase()) &&
            Strings.isNotBlank(configuration.getPassword())) {
            mongoClientSettingsBuilder.credential(MongoCredential.createCredential(configuration.getUsername(), configuration.getDatabase(), configuration.getPassword().toCharArray()));
        }

        return mongoClientSettingsBuilder.build();
    }
}
