/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.repository;

import fr.dademo.supervision.backends.postgresql.repository.entities.DatabaseStatisticsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Nonnull;
import java.util.List;

import static fr.dademo.supervision.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
public class DatabasesStatisticsQueryRepositoryImpl implements DatabasesStatisticsQueryRepository {

    /*
datname 	name 	Name of this database, or NULL for the shared objects.
numbackends 	integer 	Number of backends currently connected to this database, or NULL for the shared objects. This is the only column in this view that returns a value reflecting current state; all other columns return the accumulated values since the last reset.
xact_commit 	bigint 	Number of transactions in this database that have been committed
xact_rollback 	bigint 	Number of transactions in this database that have been rolled back
blks_read 	bigint 	Number of disk blocks read in this database
blks_hit 	bigint 	Number of times disk blocks were found already in the buffer cache, so that a read was not necessary (this only includes hits in the PostgreSQL buffer cache, not the operating system's file system cache)
tup_returned 	bigint 	Number of rows returned by queries in this database
tup_fetched 	bigint 	Number of rows fetched by queries in this database
tup_inserted 	bigint 	Number of rows inserted by queries in this database
tup_updated 	bigint 	Number of rows updated by queries in this database
tup_deleted 	bigint 	Number of rows deleted by queries in this database
conflicts 	bigint 	Number of queries canceled due to conflicts with recovery in this database. (Conflicts occur only on standby servers; see pg_stat_database_conflicts for details.)
temp_files 	bigint 	Number of temporary files created by queries in this database. All temporary files are counted, regardless of why the temporary file was created (e.g., sorting or hashing), and regardless of the log_temp_files setting.
temp_bytes 	bigint 	Total amount of data written to temporary files by queries in this database. All temporary files are counted, regardless of why the temporary file was created, and regardless of the log_temp_files setting.
deadlocks 	bigint 	Number of deadlocks detected in this database
blk_read_time 	double precision 	Time spent reading data file blocks by backends in this database, in milliseconds
blk_write_time 	double precision 	Time spent writing data file blocks by backends in this database, in milliseconds
stats_reset 	timestamp with time zone 	Time at which these statistics were last reset
     */

    private static final String QUERY = "" +
        "SELECT " +
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
        "  TEMP_FILES, " +
        "  TEMP_BYTES, " +
        "  DEADLOCKS, " +
        "  BLK_READ_TIME, " +
        "  BLK_WRITE_TIME, " +
        "  STATS_RESET " +
        "FROM PG_CATALOG.PG_STAT_DATABASE";

    @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nonnull
    @Override
    public List<DatabaseStatisticsEntity> getDatabasesStatistics() {

        return jdbcTemplate.query(QUERY, new DatabaseStatisticsEntity.DatabaseStatisticsRowMapper());
    }
}
