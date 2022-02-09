/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository.entities;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseConnectionState;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.RowMapperUtilities.validateField;

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
        public DatabaseConnectionEntity mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {

            final var objectBuilder = DatabaseConnectionEntity.builder()
                .connectionState(getConnectionStateFromValue(validateField(rs.getString(1), rs)).orElse(null))
                .connectionPID(validateField(rs.getLong(2), rs))
                .connectedDatabaseName(validateField(rs.getString(3), rs))
                .userName(validateField(rs.getString(4), rs))
                .applicationName(validateField(rs.getString(5), rs))
                .clientAddress(mapToInet(validateField(rs.getString(6), rs)))
                .clientHostname(validateField(rs.getString(7), rs))
                .clientPort(validateField(rs.getLong(8), rs))
                .connectionStart(validateField(rs.getTimestamp(9), rs))
                .transactionStart(validateField(rs.getTimestamp(10), rs))
                .lastQueryStart(validateField(rs.getTimestamp(11), rs))
                .lastStateChange(validateField(rs.getTimestamp(12), rs))
                .waitEventType(validateField(rs.getString(13), rs))
                .waitEventName(validateField(rs.getString(14), rs))
                .lastQuery(validateField(rs.getString(15), rs));

            try {
                objectBuilder.backendTypeName(rs.getString(16));
            } catch (SQLException e) {
                objectBuilder.backendTypeName(null);
            }

            return objectBuilder.build();
        }

        private Optional<DatabaseConnectionState> getConnectionStateFromValue(String value) {

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
