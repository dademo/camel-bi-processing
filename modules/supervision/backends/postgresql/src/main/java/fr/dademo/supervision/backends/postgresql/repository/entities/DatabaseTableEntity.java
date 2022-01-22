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

import static fr.dademo.supervision.backends.postgresql.repository.entities.RowMapperUtilities.validateField;

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
                .schema(validateField(rs.getString(1), rs))
                .name(validateField(rs.getString(2), rs))
                .type(validateField(rs.getString(3), rs))
                .sequentialScansCount(validateField(rs.getLong(4), rs))
                .sequentialScansFetchedRowsCount(validateField(rs.getLong(5), rs))
                .indexScansCount(validateField(rs.getLong(6), rs))
                .indexScansFetchedRowsCount(validateField(rs.getLong(7), rs))
                .insertedRowsCount(validateField(rs.getLong(8), rs))
                .updatedRowsCount(validateField(rs.getLong(9), rs))
                .deletedRowsCount(validateField(rs.getLong(10), rs))
                .hotUpdatedRowsCount(validateField(rs.getLong(11), rs))
                .liveRowsCount(validateField(rs.getLong(12), rs))
                .deadRowsCount(validateField(rs.getLong(13), rs))
                .lastVacuum(validateField(rs.getTimestamp(14), rs))
                .lastAutoVacuum(validateField(rs.getTimestamp(15), rs))
                .lastAnalyze(validateField(rs.getTimestamp(16), rs))
                .lastAutoAnalyze(validateField(rs.getTimestamp(17), rs))
                .vacuumCount(validateField(rs.getLong(18), rs))
                .autoVacuumCount(validateField(rs.getLong(19), rs))
                .analyzeCount(validateField(rs.getLong(20), rs))
                .autoAnalyzeCount(validateField(rs.getLong(21), rs))
                .tableBlocksDiskRead(validateField(rs.getLong(22), rs))
                .tableBlocksCacheRead(validateField(rs.getLong(23), rs))
                .indexesDiskRead(validateField(rs.getLong(24), rs))
                .indexesCacheRead(validateField(rs.getLong(25), rs))
                .tableToastDiskRead(validateField(rs.getLong(26), rs))
                .tableToastCacheRead(validateField(rs.getLong(27), rs))
                .indexesToastDiskRead(validateField(rs.getLong(28), rs))
                .indexesToastCacheRead(validateField(rs.getLong(29), rs))
                .viewExpression(validateField(rs.getString(30), rs))
                .build();
        }
    }
}
