/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.repository.entities;

import fr.dademo.supervision.backends.model.database.resources.DatabaseConnectionState;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nullable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author dademo
 */
@Value
@Builder
public class DatabaseConnectionEntity {

    @Nullable
    DatabaseConnectionState connectionState;

    @Nullable
    Long connectionPID;

    @Nullable
    String connectedDatabaseName;

    @Nullable
    String userName;

    @Nullable
    String applicationName;

    @Nullable
    InetAddress clientAddress;

    @Nullable
    String clientHostname;

    @Nullable
    Long clientPort;

    @Nullable
    Date connectionStart;

    @Nullable
    Date transactionStart;

    @Nullable
    Date lastQueryStart;

    @Nullable
    Date lastStateChange;

    @Nullable
    String waitEventType;

    @Nullable
    String waitEventName;

    @Nullable
    String lastQuery;

    @Nullable
    String backendTypeName; // Only in PostgreSQL 10+

    @Slf4j
    public static class DatabaseConnectionEntityRowMapper implements RowMapper<DatabaseConnectionEntity> {

        @Override
        public DatabaseConnectionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

            final var objectBuilder = DatabaseConnectionEntity.builder()
                .connectionState(getFromValue(rs.getString(1)).orElse(null))
                .connectionPID(rs.getLong(2))
                .connectedDatabaseName(rs.getString(3))
                .userName(rs.getString(4))
                .applicationName(rs.getString(5))
                .clientAddress(mapToInet(rs.getString(6)))
                .clientHostname(rs.getString(7))
                .clientPort(rs.getLong(8))
                .connectionStart(rs.getTimestamp(9))
                .transactionStart(rs.getTimestamp(10))
                .lastQueryStart(rs.getTimestamp(11))
                .lastStateChange(rs.getTimestamp(12))
                .waitEventType(rs.getString(13))
                .waitEventName(rs.getString(14))
                .lastQuery(rs.getString(15));

            try {
                objectBuilder.backendTypeName(rs.getString(16));
            } catch (SQLException e) {
                objectBuilder.backendTypeName(null);
            }

            return objectBuilder.build();
        }

        private Optional<DatabaseConnectionState> getFromValue(String value) {

            return Arrays.stream(DatabaseConnectionState.values())
                .filter(databaseConnectionState -> Objects.equals(value, databaseConnectionState.getValue()))
                .findFirst();
        }

        private InetAddress mapToInet(String value) {
            try {
                return InetAddress.getByName(value);
            } catch (UnknownHostException e) {
                log.debug("An error occurred while running query", e);
                return null;
            }
        }
    }
}
