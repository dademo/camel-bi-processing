package fr.dademo.bi.companies.configuration.exceptions;

import java.util.function.Supplier;

public class MissingDataSourceConfigurationException extends RuntimeException {

    public MissingDataSourceConfigurationException(String dataSourceTypeDescription, String dataSourceName) {
        super(String.format("Missing `%s` datasource configuration for `%s`", dataSourceTypeDescription, dataSourceName));
    }

    public static Supplier<MissingDataSourceConfigurationException> ofDataSource(String dataSourceTypeDescription, String dataSourceName) {
        return () -> new MissingDataSourceConfigurationException(dataSourceTypeDescription, dataSourceName);
    }
}
