package fr.dademo.bi.companies.beans.exceptions;

import java.util.function.Supplier;

public class MissingMigrationPathException extends RuntimeException {

    public MissingMigrationPathException(String dataSourceName) {
        super(String.format("Missing migration path configuration for data source `%s`", dataSourceName));
    }

    public static Supplier<MissingMigrationPathException> ofDataSource(String dataSourceName) {
        return () -> new MissingMigrationPathException(dataSourceName);
    }
}
