/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.repository;

import fr.dademo.supervision.backends.postgresql.repository.entities.DatabaseTableRowsCountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Objects;

import static fr.dademo.supervision.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
@Repository
public class DatabaseTableRowsCountQueryRepositoryImpl implements DatabaseTableRowsCountQueryRepository {

    private static final String QUERY = "" +
        "SELECT COUNT(*) FROM \"%s\".\"%s\"";

    @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nonnull
    @Override
    public DatabaseTableRowsCountEntity getRowCountForTable(@Nonnull String tableSchema, @Nonnull String tableName) {

        return Objects.requireNonNull(jdbcTemplate.queryForObject(
            String.format(QUERY, tableSchema, tableName),
            new DatabaseTableRowsCountEntity.DatabaseTableRowsCountRowMapper()
        ));
    }
}
