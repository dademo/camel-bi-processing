package fr.dademo.bi.companies.configuration;

import fr.dademo.bi.companies.configuration.data_sources.JDBCDataSourceConfiguration;
import fr.dademo.bi.companies.configuration.data_sources.MongoDBClientConfiguration;
import fr.dademo.bi.companies.configuration.exceptions.MissingDataSourceConfigurationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "datasources.jdbc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourcesConfiguration {

    private static final String JDBC_TYPE = "JDBC";
    private static final String MONGODB_TYPE = "MongoDB";

    @Nonnull
    private Map<String, JDBCDataSourceConfiguration> jdbc;

    @Nonnull
    private Map<String, MongoDBClientConfiguration> mongodb;

    public JDBCDataSourceConfiguration getJDBCDataSourceConfigurationByName(@Nonnull String dataSourceName) {
        return configurationByName(jdbc, dataSourceName, JDBC_TYPE);
    }

    public MongoDBClientConfiguration getMongoDBClientConfigurationByName(@Nonnull String dataSourceName) {
        return configurationByName(mongodb, dataSourceName, MONGODB_TYPE);
    }

    private <T> T configurationByName(Map<String, T> configurationMap,
                                      @Nonnull String configurationKey,
                                      @Nonnull String dataSourceType) {

        return Optional.ofNullable(configurationMap.get(configurationKey))
                .orElseThrow(MissingDataSourceConfigurationException.ofDataSource(dataSourceType, configurationKey));
    }
}
