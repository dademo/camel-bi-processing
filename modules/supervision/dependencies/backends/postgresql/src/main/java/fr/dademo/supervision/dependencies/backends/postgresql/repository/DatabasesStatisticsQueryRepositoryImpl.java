/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository;

import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseStatisticsEntity;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static fr.dademo.supervision.dependencies.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
@Slf4j
@Repository
public class DatabasesStatisticsQueryRepositoryImpl implements DatabasesStatisticsQueryRepository {

    private static final String QUERY = "SELECT " +
        "  PG_POSTMASTER_START_TIME(), " +
        "  DATNAME, " +
        "  XACT_COMMIT, " +
        "  XACT_ROLLBACK, " +
        "  BLKS_READ, " +
        "  BLKS_HIT, " +
        "  TUP_RETURNED, " +
        "  TUP_FETCHED, " +
        "  TUP_INSERTED, " +
        "  TUP_UPDATED, " +
        "  TUP_DELETED, " +
        "  CONFLICTS, " +
        "  DEADLOCKS, " +
        "  BLK_READ_TIME, " +
        "  BLK_WRITE_TIME, " +
        "  STATS_RESET " +
        "FROM PG_CATALOG.PG_STAT_DATABASE";

    private final JdbcTemplate jdbcTemplate;

    public DatabasesStatisticsQueryRepositoryImpl(@Nonnull @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME) JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public List<DatabaseStatisticsEntity> getDatabasesStatistics() {

        log.debug("Getting databases global statistics");
        return jdbcTemplate.query(QUERY, new DatabaseStatisticsEntity.DatabaseStatisticsRowMapper());
    }
}
