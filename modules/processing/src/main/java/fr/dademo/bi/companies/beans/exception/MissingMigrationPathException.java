package fr.dademo.bi.companies.beans.exception;

import java.util.function.Supplier;

public class MissingMigrationPathException extends RuntimeException {

    private static final long serialVersionUID = 8477372149460991444L;

    public MissingMigrationPathException(String dataSourceName) {
        super(String.format("Missing migration path configuration for data source `%s`", dataSourceName));
    }

    public static Supplier<MissingMigrationPathException> ofDataSource(String dataSourceName) {
        return () -> new MissingMigrationPathException(dataSourceName);
    }
}
