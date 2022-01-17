/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.repository;

import fr.dademo.supervision.backends.postgresql.repository.entities.DatabaseConnectionEntity;
import fr.dademo.supervision.backends.postgresql.repository.exceptions.QueryError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static fr.dademo.supervision.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
@Slf4j
@Repository
public class DatabasesConnectionsQueryRepositoryImpl implements DatabasesConnectionsQueryRepository {

    private static final String QUERY = "" +
        "SELECT " +
        "  STATE, " +
        "  PID, " +
        "  DATNAME, " +
        "  USENAME, " +
        "  APPLICATION_NAME, " +
        "  REGEXP_REPLACE(CLIENT_ADDR::VARCHAR, '\\/.+$', ''), " +
        "  CLIENT_HOSTNAME, " +
        "  CLIENT_PORT, " +
        "  BACKEND_START, " +
        "  XACT_START, " +
        "  QUERY_START, " +
        "  STATE_CHANGE, " +
        "  WAIT_EVENT_TYPE, " +
        "  WAIT_EVENT, " +
        "  QUERY %s " +
        "FROM PG_CATALOG.PG_STAT_ACTIVITY ";

    @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nonnull
    @Override
    public List<DatabaseConnectionEntity> getDatabasesConnections() {

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
