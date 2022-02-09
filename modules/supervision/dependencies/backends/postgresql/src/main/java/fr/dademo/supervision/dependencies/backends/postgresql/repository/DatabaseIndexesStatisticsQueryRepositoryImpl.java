/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository;

import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseIndexEntity;
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
public class DatabaseIndexesStatisticsQueryRepositoryImpl implements DatabaseIndexesStatisticsQueryRepository {

    private static final String QUERY = "" +
        "SELECT " +
        "  SCHEMANAME, " +
        "  RELNAME, " +
        "  INDEXRELNAME, " +
        "  IDX_SCAN, " +
        "  IDX_TUP_READ, " +
        "  IDX_TUP_FETCH " +
        "FROM PG_STAT_ALL_INDEXES ";

    @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nonnull
    @Override
    public List<DatabaseIndexEntity> getDatabaseIndexesStatistics() {

        log.debug("Getting database indexes");
        return jdbcTemplate.query(QUERY, new DatabaseIndexEntity.DatabaseIndexEntityRowMapper());
    }
}
