/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.repository;

import fr.dademo.supervision.backends.postgresql.repository.entities.DatabaseIndexEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Nonnull;
import java.util.List;

import static fr.dademo.supervision.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
public class DatabaseIndexQueryRepositoryImpl implements DatabaseIndexQueryRepository {

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
    public List<DatabaseIndexEntity> getDatabaseIndexes() {
        return jdbcTemplate.query(QUERY, new DatabaseIndexEntity.DatabaseIndexEntityRowMapper());
    }
}
