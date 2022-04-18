/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository.entities;

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
import java.time.Duration;
import java.util.Optional;

import static fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.RowMapperUtilities.validateField;

/**
 * @author dademo
 */
@Value
@Builder
public class DatabaseReplicationPeerEntity {

    @Nonnull
    Boolean isPrimary;

    @Nullable
    String status;

    @Nullable
    Long replicationPID;

    @Nullable
    String useName;

    @Nullable
    String applicationName;

    @Nullable
    String slotName;

    @Nullable
    InetAddress peerAddress;

    @Nullable
    String peerHostName;

    @Nullable
    Long peerPort;

    @Nullable
    String state;

    @Nullable
    String syncState;

    @Nullable
    Duration replicationDelay;

    @Nullable
    Long sendingDelaySize;

    @Nullable
    Long receivingDelaySize;

    @Nullable
    Long replayingDelaySize;

    @Nullable
    Long totalDelaySize;


    @Slf4j
    public static class DatabaseReplicationPeerControllerRowMapper implements RowMapper<DatabaseReplicationPeerEntity> {

        @Override
        public DatabaseReplicationPeerEntity mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {

            return DatabaseReplicationPeerEntity.builder()
                .isPrimary(true)
                .replicationPID(validateField(rs.getLong(1), rs))
                .useName(validateField(rs.getString(2), rs))
                .applicationName(validateField(rs.getString(3), rs))
                .peerAddress(mapToInet(validateField(rs.getString(4), rs)))
                .peerHostName(validateField(rs.getString(5), rs))
                .peerPort(validateField(rs.getLong(6), rs))
                .state(validateField(rs.getString(7), rs))
                .syncState(validateField(rs.getString(8), rs))
                .replicationDelay(validateField(fromDurationMilliseconds(rs.getDouble(9)), rs))
                .sendingDelaySize(validateField(rs.getLong(10), rs))
                .receivingDelaySize(validateField(rs.getLong(11), rs))
                .replayingDelaySize(validateField(rs.getLong(12), rs))
                .totalDelaySize(validateField(rs.getLong(13), rs))
                .build();
        }

        private Duration fromDurationMilliseconds(Double durationMilliseconds) {
            return Duration.ofMillis(durationMilliseconds.longValue());
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


    @Slf4j
    public static class DatabaseReplicationPeerClientRowMapper implements RowMapper<DatabaseReplicationPeerEntity> {

        @Override
        public DatabaseReplicationPeerEntity mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {

            final var hostName = Optional.ofNullable(validateField(rs.getString(4), rs));

            return DatabaseReplicationPeerEntity.builder()
                .isPrimary(false)
                .replicationPID(validateField(rs.getLong(1), rs))
                .status(validateField(rs.getString(2), rs))
                .slotName(validateField(rs.getString(3), rs))
                .peerAddress(hostName.map(this::tryMapToInet).orElse(null))
                .peerHostName(hostName.orElse(null))
                .peerPort(validateField(rs.getLong(5), rs))
                .replicationDelay(validateField(fromDurationMilliseconds(rs.getDouble(6)), rs))
                .build();
        }

        private Duration fromDurationMilliseconds(Double durationMilliseconds) {
            return Duration.ofMillis(durationMilliseconds.longValue());
        }

        private InetAddress tryMapToInet(String value) {
            try {
                return InetAddress.getByName(value);
            } catch (UnknownHostException e) {
                log.debug("An error occurred while running query", e);
                return null;
            }
        }
    }
}
