/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository;

import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseReplicationPeerEntity;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static fr.dademo.supervision.dependencies.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
@Slf4j
@Repository
public class DatabaseReplicationPeerQueryRepositoryImpl implements DatabaseReplicationPeerQueryRepository {

    private static final String CONTROLLER_QUERY = "" +
        "SELECT" +
        "   PID, " +
        "   USENAME, " +
        "   APPLICATION_NAME, " +
        "   REGEXP_REPLACE(CLIENT_ADDR::VARCHAR, '\\/.+$', '')  AS CLIENT_ADDRESS, " +
        "   CLIENT_HOSTNAME, " +
        "   CLIENT_PORT, " +
        "   STATE, " +
        "   SYNC_STATE, " +
        "   NOW() - REPLY_TIME                                  AS REPLICATION_DELAY, " +
        "   PG_WAL_LSN_DIFF(PG_CURRENT_WAL_LSN(), SENT_LSN)     AS SENDING_DELAY_SIZE, " +
        "   PG_WAL_LSN_DIFF(SENT_LSN, FLUSH_LSN)                AS RECEIVING_DELAY_SIZE, " +
        "   PG_WAL_LSN_DIFF(FLUSH_LSN, REPLAY_LSN)              AS REPLAYING_DELAY_SIZE, " +
        "   PG_WAL_LSN_DIFF(PG_CURRENT_WAL_LSN(), REPLAY_LSN)   AS TOTAL_DELAY_SIZE " +
        "FROM PG_STAT_REPLICATION";

    private static final String CLIENT_QUERY = "" +
        "SELECT" +
        "   PID, " +
        "   STATUS, " +
        "   SLOT_NAME, " +
        "   SENDER_HOST, " +
        "   SLOT_NAME, " +
        "   CASE " +
        "       WHEN PG_LAST_WAL_RECEIVE_LSN() = PG_LAST_WAL_REPLAY_LSN() " +
        "       THEN 0 " +
        "       ELSE EXTRACT (EPOCH FROM NOW() - PG_LAST_XACT_REPLAY_TIMESTAMP()) " +
        "   END AS REPLICATION_DELAY " +
        "FROM PG_STAT_WAL_RECEIVER";

    @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nonnull
    @Override
    public List<DatabaseReplicationPeerEntity> getDatabaseControllerReplicationInformations() {

        log.debug("Getting controller replication informations");

        return Stream.of(
                CONTROLLER_QUERY,
                CONTROLLER_QUERY
                    .replace("LSN", "LOCATION")
                    .replace("WAL", "XLOG")
            )
            .map(query -> applyQuery(
                query,
                new DatabaseReplicationPeerEntity.DatabaseReplicationPeerControllerRowMapper()
            ))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst()
            .orElse(Collections.emptyList());
    }

    @Nonnull
    @Override
    public List<DatabaseReplicationPeerEntity> getDatabaseClientReplicationInformations() {

        log.debug("Getting client replication informations");
        return applyQuery(CLIENT_QUERY, new DatabaseReplicationPeerEntity.DatabaseReplicationPeerClientRowMapper())
            .orElse(Collections.emptyList());
    }

    private <T> Optional<List<T>> applyQuery(String query, RowMapper<T> mapper) {

        try {
            return Optional.of(jdbcTemplate.query(query, mapper));
        } catch (BadSqlGrammarException e) {
            log.debug("An error occurred while running query", e);
            return Optional.empty();
        }
    }
}
