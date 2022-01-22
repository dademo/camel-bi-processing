/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.repository.entities;

import lombok.Builder;
import lombok.Value;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;

import static fr.dademo.supervision.backends.postgresql.repository.entities.RowMapperUtilities.validateField;

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
        public DatabaseStatisticsEntity mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {

            return DatabaseStatisticsEntity.builder()
                .startTime(validateField(rs.getTimestamp(1), rs))
                .name(validateField(rs.getString(2), rs))
                .commitCounts(validateField(rs.getLong(3), rs))
                .rollbackCounts(validateField(rs.getLong(4), rs))
                .bufferBlocksRead(validateField(rs.getLong(5), rs))
                .diskBlocksRead(validateField(rs.getLong(6), rs))
                .returnedRowsCount(validateField(rs.getLong(7), rs))
                .fetchedRowsCount(validateField(rs.getLong(8), rs))
                .insertedRowsCount(validateField(rs.getLong(9), rs))
                .updatedRowsCount(validateField(rs.getLong(10), rs))
                .deletedRowsCount(validateField(rs.getLong(11), rs))
                .conflictsCount(validateField(rs.getLong(12), rs))
                .deadlocksCount(validateField(rs.getLong(13), rs))
                .readTime(validateField(fromDurationMilliseconds(rs.getDouble(14)), rs))
                .writeTime(validateField(fromDurationMilliseconds(rs.getDouble(15)), rs))
                .lastStatisticsResetTime(validateField(rs.getTimestamp(16), rs))
                .build();
        }

        private Duration fromDurationMilliseconds(Double durationMilliseconds) {
            return Duration.ofMillis(durationMilliseconds.longValue());
        }
    }
}
