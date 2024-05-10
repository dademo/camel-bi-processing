/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository;

import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseTableRowsCountEntity;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static fr.dademo.supervision.dependencies.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
@Slf4j
@Repository
public class DatabaseTableRowsCountQueryRepositoryImpl implements DatabaseTableRowsCountQueryRepository {

    private static final String QUERY = "SELECT COUNT(*) FROM \"%s\".\"%s\"";

    private final JdbcTemplate jdbcTemplate;

    public DatabaseTableRowsCountQueryRepositoryImpl(@Nonnull @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME) JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
