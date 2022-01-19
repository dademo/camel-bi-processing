/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.repository.entities;

import lombok.Builder;
import lombok.Value;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;

/**
 * @author dademo
 */
@Value
@Builder
public class DatabaseStatisticsEntity {

    @Nullable
    Date startTime;

    @Nullable
    String name;

    @Nullable
    Long commitCounts;

    @Nullable
    Long rollbackCounts;

    @Nullable
    Long bufferBlocksRead;

    @Nullable
    Long diskBlocksRead;

    @Nullable
    Long returnedRowsCount;

    @Nullable
    Long fetchedRowsCount;

    @Nullable
    Long insertedRowsCount;

    @Nullable
    Long updatedRowsCount;

    @Nullable
    Long deletedRowsCount;

    @Nullable
    Long conflictsCount;

    @Nullable
    Long deadlocksCount;

    @Nullable
    Duration readTime;

    @Nullable
    Duration writeTime;

    @Nullable
    Date lastStatisticsResetTime;

    public static class DatabaseStatisticsRowMapper implements RowMapper<DatabaseStatisticsEntity> {

        @Override
        public DatabaseStatisticsEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

            return DatabaseStatisticsEntity.builder()
                .startTime(rs.getTimestamp(1))
                .name(rs.getString(2))
                .commitCounts(rs.getLong(3))
                .rollbackCounts(rs.getLong(4))
                .bufferBlocksRead(rs.getLong(5))
                .diskBlocksRead(rs.getLong(6))
                .returnedRowsCount(rs.getLong(7))
                .fetchedRowsCount(rs.getLong(8))
                .insertedRowsCount(rs.getLong(9))
                .updatedRowsCount(rs.getLong(10))
                .deletedRowsCount(rs.getLong(11))
                .conflictsCount(rs.getLong(12))
                .deadlocksCount(rs.getLong(13))
                .readTime(fromDurationMilliseconds(rs.getDouble(14)))
                .writeTime(fromDurationMilliseconds(rs.getDouble(15)))
                .lastStatisticsResetTime(rs.getTimestamp(16))
                .build();
        }

        private Duration fromDurationMilliseconds(Double durationMilliseconds) {
            return Duration.ofMillis(durationMilliseconds.longValue());
        }
    }
}
