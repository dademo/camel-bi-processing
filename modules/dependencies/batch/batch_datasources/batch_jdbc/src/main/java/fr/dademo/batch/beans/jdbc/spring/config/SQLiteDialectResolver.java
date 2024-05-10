/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.jdbc.spring.config;

import fr.dademo.batch.beans.jdbc.spring.dialect.SQLiteDialect;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

/**
 * @author dademo
 */
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
