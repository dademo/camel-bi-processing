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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dademo
 */
@Value
@Builder
public class DatabaseTableRowsCountEntity {

    @Nonnull
    Long rowCount;

    public static class DatabaseTableRowsCountRowMapper implements RowMapper<DatabaseTableRowsCountEntity> {

        @Override
        public DatabaseTableRowsCountEntity mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {

            return DatabaseTableRowsCountEntity.builder()
                .rowCount(rs.getLong(1))
                .build();
        }
    }
}
