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

/**
 * @author dademo
 */
@Value
@Builder
public class DatabaseIndexEntity {

    @Nonnull
    String schema;

    @Nonnull
    String name;

    @Nonnull
    String tableName;

    /* https://www.postgresql.org/docs/9.2/monitoring-stats.html#PG-STAT-ALL-TABLES-VIEW */
    @Nullable
    Long indexScansCount;

    @Nullable
    Long returnedRowsCount;

    @Nullable
    Long fetchedRowsCount;

    public static class DatabaseIndexEntityRowMapper implements RowMapper<DatabaseIndexEntity> {

        @Override
        public DatabaseIndexEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return DatabaseIndexEntity.builder()
                .schema(rs.getString(1))
                .name(rs.getString(2))
                .tableName(rs.getString(3))
                .indexScansCount(rs.getLong(4))
                .returnedRowsCount(rs.getLong(5))
                .fetchedRowsCount(rs.getLong(6))
                .build();
        }
    }
}
