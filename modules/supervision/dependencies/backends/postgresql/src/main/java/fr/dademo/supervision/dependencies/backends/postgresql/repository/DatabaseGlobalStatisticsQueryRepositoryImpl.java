/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static fr.dademo.supervision.dependencies.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
@Slf4j
@Repository
public class DatabaseGlobalStatisticsQueryRepositoryImpl implements DatabaseGlobalStatisticsQueryRepository {

    private static final String QUERY = "SELECT PG_POSTMASTER_START_TIME()";

    private final JdbcTemplate jdbcTemplate;

    public DatabaseGlobalStatisticsQueryRepositoryImpl(@Nonnull @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME) JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Date getDatabaseStartTime() {

        log.debug("Getting database start time");
        return jdbcTemplate.queryForObject(QUERY, new RowDateMapper());
    }

    private static class RowDateMapper implements RowMapper<Date> {

        @Override
        public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getTimestamp(1);
        }
    }
}
