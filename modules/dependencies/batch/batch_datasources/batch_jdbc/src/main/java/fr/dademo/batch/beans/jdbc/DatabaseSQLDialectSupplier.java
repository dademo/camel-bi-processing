/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.jdbc;

import lombok.SneakyThrows;
import org.jooq.SQLDialect;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author dademo
 */
public class DatabaseSQLDialectSupplier implements Supplier<SQLDialect> {

    private final DataSource dataSource;
    private SQLDialect cachedSqlDialect = null;

    public DatabaseSQLDialectSupplier(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SQLDialect get() {

        return Optional.ofNullable(cachedSqlDialect)
            .orElseGet(this::getAndCacheSqlDialect);
    }

    // https://stackoverflow.com/questions/9320200/inline-blob-binary-data-types-in-sql-jdbc/58736912#58736912
    @SneakyThrows
    private SQLDialect getAndCacheSqlDialect() {

        try (var connection = dataSource.getConnection()) {
            final var databaseProductName = connection.getMetaData().getDatabaseProductName().toLowerCase();
            cachedSqlDialect = Arrays.stream(SQLDialect.values())
                .filter(v -> !v.getNameLC().isBlank())
                .filter(v -> databaseProductName.startsWith(v.getNameLC()))
                .findFirst().orElse(SQLDialect.DEFAULT);
            return cachedSqlDialect;
        }
    }
}
