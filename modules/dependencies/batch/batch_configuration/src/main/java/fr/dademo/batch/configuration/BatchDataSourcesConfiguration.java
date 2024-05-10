/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration;

import fr.dademo.batch.configuration.data_sources.AmqpConfiguration;
import fr.dademo.batch.configuration.data_sources.JDBCDataSourceConfiguration;
import fr.dademo.batch.configuration.data_sources.MongoDBClientConfiguration;
import fr.dademo.batch.configuration.exception.MissingDataSourceConfigurationException;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author dademo
 */
@Configuration
@ConfigurationProperties(prefix = "datasources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchDataSourcesConfiguration {

    private static final String JDBC_TYPE = "JDBC";
    private static final String MONGODB_TYPE = "MongoDB";
    private static final String AMQP_TYPE = "Amqp";

    @Nonnull
    private Map<String, JDBCDataSourceConfiguration> jdbc = new HashMap<>();

    @Nonnull
    private Map<String, MongoDBClientConfiguration> mongodb = new HashMap<>();

    @Nonnull
    private Map<String, AmqpConfiguration> amqp = new HashMap<>();

    @Nonnull
    public JDBCDataSourceConfiguration getJDBCDataSourceConfigurationByName(@Nonnull String dataSourceName) {
        return configurationByName(jdbc, dataSourceName, JDBC_TYPE);
    }

    @Nonnull
    public MongoDBClientConfiguration getMongoDBClientConfigurationByName(@Nonnull String dataSourceName) {
        return configurationByName(mongodb, dataSourceName, MONGODB_TYPE);
    }

    @Nonnull
    public AmqpConfiguration getAmqpConfigurationByName(@Nonnull String dataSourceName) {
        return configurationByName(amqp, dataSourceName, AMQP_TYPE);
    }

    @Nonnull
    private <T> T configurationByName(Map<String, T> configurationMap,
                                      @Nonnull String configurationKey,
                                      @Nonnull String dataSourceTypeDescription) {

        return Optional.ofNullable(configurationMap.get(configurationKey))
            .orElseThrow(MissingDataSourceConfigurationException.ofDataSource(dataSourceTypeDescription, configurationKey));
    }
}
