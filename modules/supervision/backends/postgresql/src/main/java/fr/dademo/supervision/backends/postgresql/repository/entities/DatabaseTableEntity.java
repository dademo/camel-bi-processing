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
import java.util.Date;

/**
 * @author dademo
 */
@Value
@Builder
public class DatabaseTableEntity {

    @Nonnull
    String schema;

    @Nonnull
    String name;

    @Nonnull
    String type;    // BASE TABLE | VIEW

    @Nullable
    Long sequentialScansCount;

    @Nullable
    Long sequentialScansFetchedRowsCount;

    @Nullable
    Long indexScansCount;

    @Nullable
    Long indexScansFetchedRowsCount;

    @Nullable
    Long insertedRowsCount;

    @Nullable
    Long updatedRowsCount;

    @Nullable
    Long deletedRowsCount;

    @Nullable
    Long hotUpdatedRowsCount;

    @Nullable
    Long liveRowsCount;

    @Nullable
    Long deadRowsCount;

    @Nullable
    Date lastVacuum;

    @Nullable
    Date lastAutoVacuum;

    @Nullable
    Date lastAnalyze;

    @Nullable
    Date lastAutoAnalyze;

    @Nullable
    Long vacuumCount;

    @Nullable
    Long autoVacuumCount;

    @Nullable
    Long analyzeCount;

    @Nullable
    Long autoAnalyzeCount;

    /* https://www.postgresql.org/docs/9.2/monitoring-stats.html#PG-STATIO-ALL-TABLES-VIEW */
    @Nullable
    Long tableBlocksDiskRead;

    @Nullable
    Long tableBlocksCacheRead;

    @Nullable
    Long indexesDiskRead;

    @Nullable
    Long indexesCacheRead;

    @Nullable
    Long tableToastDiskRead;

    @Nullable
    Long tableToastCacheRead;

    @Nullable
    Long indexesToastDiskRead;

    @Nullable
    Long indexesToastCacheRead;

    @Nullable
    String viewExpression;

    public static class DatabaseTableRowMapper implements RowMapper<DatabaseTableEntity> {

        @Override
        public DatabaseTableEntity mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {

            return DatabaseTableEntity.builder()
                .schema(rs.getString(1))
                .name(rs.getString(2))
                .type(rs.getString(3))
                .sequentialScansCount(rs.getLong(4))
                .sequentialScansFetchedRowsCount(rs.getLong(5))
                .indexScansCount(rs.getLong(6))
                .indexScansFetchedRowsCount(rs.getLong(7))
                .insertedRowsCount(rs.getLong(8))
                .updatedRowsCount(rs.getLong(9))
                .deletedRowsCount(rs.getLong(10))
                .hotUpdatedRowsCount(rs.getLong(11))
                .liveRowsCount(rs.getLong(12))
                .deadRowsCount(rs.getLong(13))
                .lastVacuum(rs.getDate(14))
                .lastAutoVacuum(rs.getDate(15))
                .lastAnalyze(rs.getDate(16))
                .lastAutoAnalyze(rs.getDate(17))
                .vacuumCount(rs.getLong(18))
                .autoVacuumCount(rs.getLong(19))
                .analyzeCount(rs.getLong(20))
                .autoAnalyzeCount(rs.getLong(21))
                .tableBlocksDiskRead(rs.getLong(22))
                .tableBlocksCacheRead(rs.getLong(23))
                .indexesDiskRead(rs.getLong(24))
                .indexesCacheRead(rs.getLong(25))
                .tableToastDiskRead(rs.getLong(26))
                .tableToastCacheRead(rs.getLong(27))
                .indexesToastDiskRead(rs.getLong(28))
                .indexesToastCacheRead(rs.getLong(29))
                .viewExpression(rs.getString(30))
                .build();
        }
    }
}
