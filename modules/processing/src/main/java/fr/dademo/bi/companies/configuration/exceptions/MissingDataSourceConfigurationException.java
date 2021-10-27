package fr.dademo.bi.companies.configuration.exceptions;

import java.util.function.Supplier;

public class MissingDataSourceConfigurationException extends RuntimeException {

    public MissingDataSourceConfigurationException(String dataSourceType, String dataSourceName) {
        super(String.format("Missing `%s` datasource configuration for `%s`", dataSourceType, dataSourceName));
    }

    public static Supplier<MissingDataSourceConfigurationException> ofDataSource(String dataSourceType, String dataSourceName) {
        return () -> new MissingDataSourceConfigurationException(dataSourceType, dataSourceName);
    }
}
