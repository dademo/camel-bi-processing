/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository;

import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseConnectionEntity;
import fr.dademo.supervision.dependencies.backends.postgresql.repository.exceptions.QueryError;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static fr.dademo.supervision.dependencies.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
@Slf4j
@Repository
public class DatabasesConnectionsQueryRepositoryImpl implements DatabasesConnectionsQueryRepository {

    private static final String QUERY = "SELECT " +
        "  STATE, " +
        "  PID, " +
        "  DATNAME, " +
        "  USENAME, " +
        "  CASE LENGTH(TRIM(APPLICATION_NAME))" +
        "    WHEN 0 THEN NULL" +
        "    ELSE APPLICATION_NAME" +
        "  END AS APPLICATION_NAME, " +
        "  REGEXP_REPLACE(CLIENT_ADDR::VARCHAR, '\\/.+$', '')   AS CLIENT_ADDRESS, " +
        "  CLIENT_HOSTNAME, " +
        "  CLIENT_PORT, " +
        "  BACKEND_START, " +
        "  XACT_START, " +
        "  QUERY_START, " +
        "  STATE_CHANGE, " +
        "  WAIT_EVENT_TYPE, " +
        "  WAIT_EVENT, " +
        "  CASE LENGTH(TRIM(QUERY))" +
        "    WHEN 0 THEN NULL" +
        "    ELSE QUERY" +
        "  END AS QUERY " +
        "  %s " +
        "FROM PG_CATALOG.PG_STAT_ACTIVITY ";

    private final JdbcTemplate jdbcTemplate;

    public DatabasesConnectionsQueryRepositoryImpl(@Nonnull @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME) JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public List<DatabaseConnectionEntity> getDatabasesConnections() {

        log.debug("Getting database connections");

        return Stream.of(
                ", BACKEND_TYPE",   // v10+
                ""                  // Older versions
            )
            .map(this::applyQuery)
            .filter(v -> !v.isEmpty())
            .findFirst()
            .orElseThrow(QueryError::new);
    }

    private List<DatabaseConnectionEntity> applyQuery(String queryDynamicPart) {

        try {
            return jdbcTemplate.query(
                String.format(QUERY, queryDynamicPart),
                new DatabaseConnectionEntity.DatabaseConnectionEntityRowMapper()
            );
        } catch (DataAccessException e) {
            log.debug("An error occurred while running query", e);
            return Collections.emptyList();
        }
    }
}
