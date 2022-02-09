/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository;

import fr.dademo.supervision.dependencies.backends.postgresql.configuration.ModuleBeans;
import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseTableRowsCountEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author dademo
 */
@Slf4j
@Repository
public class DatabaseTableRowsCountQueryRepositoryImpl implements DatabaseTableRowsCountQueryRepository {

    private static final String QUERY = "" +
        "SELECT COUNT(*) FROM \"%s\".\"%s\"";

    @Qualifier(ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nonnull
    @Override
    public DatabaseTableRowsCountEntity getRowCountForTable(@Nonnull String tableSchema, @Nonnull String tableName) {

        log.debug("Getting row count for table [{}, {}]", tableSchema, tableName);
        return Objects.requireNonNull(jdbcTemplate.queryForObject(
            String.format(QUERY, tableSchema, tableName),
            new DatabaseTableRowsCountEntity.DatabaseTableRowsCountRowMapper()
        ));
    }
}
