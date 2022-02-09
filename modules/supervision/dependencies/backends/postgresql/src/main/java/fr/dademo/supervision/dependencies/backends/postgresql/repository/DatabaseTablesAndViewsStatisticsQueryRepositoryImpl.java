/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository;

import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseTableEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;

import static fr.dademo.supervision.dependencies.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
@Slf4j
@Repository
public class DatabaseTablesAndViewsStatisticsQueryRepositoryImpl implements DatabaseTablesAndViewsStatisticsQueryRepository {

    private static final String QUERY = "" +
        "SELECT " +
        "  INFO_TABLES.TABLE_SCHEMA, " +
        "  INFO_TABLES.TABLE_NAME, " +
        "  INFO_TABLES.TABLE_TYPE, " +
        "  STAT_TABLES.SEQ_SCAN, " +
        "  STAT_TABLES.SEQ_TUP_READ, " +
        "  STAT_TABLES.IDX_SCAN, " +
        "  STAT_TABLES.IDX_TUP_FETCH, " +
        "  STAT_TABLES.N_TUP_INS, " +
        "  STAT_TABLES.N_TUP_UPD, " +
        "  STAT_TABLES.N_TUP_DEL, " +
        "  STAT_TABLES.N_TUP_HOT_UPD, " +
        "  STAT_TABLES.N_LIVE_TUP, " +
        "  STAT_TABLES.N_DEAD_TUP, " +
        "  STAT_TABLES.LAST_VACUUM, " +
        "  STAT_TABLES.LAST_AUTOVACUUM, " +
        "  STAT_TABLES.LAST_ANALYZE, " +
        "  STAT_TABLES.LAST_AUTOANALYZE, " +
        "  STAT_TABLES.VACUUM_COUNT, " +
        "  STAT_TABLES.AUTOVACUUM_COUNT, " +
        "  STAT_TABLES.ANALYZE_COUNT, " +
        "  STAT_TABLES.AUTOANALYZE_COUNT, " +
        "  STAT_TABLES_IO.HEAP_BLKS_READ, " +
        "  STAT_TABLES_IO.HEAP_BLKS_HIT, " +
        "  STAT_TABLES_IO.IDX_BLKS_READ, " +
        "  STAT_TABLES_IO.IDX_BLKS_HIT, " +
        "  STAT_TABLES_IO.TOAST_BLKS_READ, " +
        "  STAT_TABLES_IO.TOAST_BLKS_HIT, " +
        "  STAT_TABLES_IO.TIDX_BLKS_READ, " +
        "  STAT_TABLES_IO.TIDX_BLKS_HIT, " +
        "  STAT_VIEWS.DEFINITION " +
        "FROM INFORMATION_SCHEMA.TABLES AS INFO_TABLES " +
        "LEFT JOIN PG_CATALOG.PG_STAT_ALL_TABLES AS STAT_TABLES " +
        "  ON STAT_TABLES.SCHEMANAME = INFO_TABLES.TABLE_SCHEMA " +
        "  AND STAT_TABLES.RELNAME = INFO_TABLES.TABLE_NAME " +
        "LEFT JOIN PG_CATALOG.PG_STATIO_ALL_TABLES AS STAT_TABLES_IO " +
        "  ON STAT_TABLES_IO.SCHEMANAME = INFO_TABLES.TABLE_SCHEMA " +
        "  AND STAT_TABLES_IO.RELNAME = INFO_TABLES.TABLE_NAME " +
        "LEFT JOIN PG_CATALOG.PG_VIEWS AS STAT_VIEWS " +
        "  ON STAT_VIEWS.SCHEMANAME = INFO_TABLES.TABLE_SCHEMA " +
        "  AND STAT_VIEWS.VIEWNAME = INFO_TABLES.TABLE_NAME";

    @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nonnull
    @Override
    public List<DatabaseTableEntity> getDatabaseTablesAndViewsStatistics() {

        log.debug("Getting tables and views statistics");
        return jdbcTemplate.query(QUERY, new DatabaseTableEntity.DatabaseTableRowMapper());
    }
}
