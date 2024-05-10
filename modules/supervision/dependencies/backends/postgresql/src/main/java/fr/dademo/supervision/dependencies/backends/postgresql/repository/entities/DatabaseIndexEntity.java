/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository.entities;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Value;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.RowMapperUtilities.validateField;

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

    @Nullable
    Long indexScansCount;

    @Nullable
    Long returnedRowsCount;

    @Nullable
    Long fetchedRowsCount;

    public static class DatabaseIndexEntityRowMapper implements RowMapper<DatabaseIndexEntity> {

        @Override
        public DatabaseIndexEntity mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
            return DatabaseIndexEntity.builder()
                .schema(validateField(rs.getString(1), rs))
                .name(validateField(rs.getString(2), rs))
                .tableName(validateField(rs.getString(3), rs))
                .indexScansCount(validateField(rs.getLong(4), rs))
                .returnedRowsCount(validateField(rs.getLong(5), rs))
                .fetchedRowsCount(validateField(rs.getLong(6), rs))
                .build();
        }
    }
}
