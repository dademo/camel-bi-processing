package fr.dademo.spring.config;

import fr.dademo.spring.dialect.SQLiteDialect;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

public class SQLiteDialectResolver implements DialectResolver.JdbcDialectProvider {

    @Nullable
    private static Dialect getDialect(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String name = metaData.getDatabaseProductName().toLowerCase(Locale.ROOT);
        if (name.contains("sqlite")) {
            return SQLiteDialect.INSTANCE;
        }
        return null;
    }

    @Override
    @Nonnull
    public Optional<Dialect> getDialect(@Nonnull JdbcOperations operations) {
        return Optional.ofNullable(
                operations.execute((ConnectionCallback<Dialect>) SQLiteDialectResolver::getDialect));
    }
}
