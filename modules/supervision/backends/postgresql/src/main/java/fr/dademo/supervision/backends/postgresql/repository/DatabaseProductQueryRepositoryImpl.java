/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.repository;

import fr.dademo.supervision.backends.postgresql.repository.entities.DatabaseProductVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Nonnull;
import java.util.Objects;

import static fr.dademo.supervision.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
public class DatabaseProductQueryRepositoryImpl implements DatabaseProductQueryRepository {

    private static final String QUERY = "" +
        "SELECT " +
        "  VERSION(), " +
        "  SETTING " +
        "FROM PG_CATALOG.PG_SETTINGS " +
        "WHERE name = 'server_version' ";

    @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nonnull
    public DatabaseProductVersion getDatabaseProductVersion() {
        return Objects.requireNonNull(
            jdbcTemplate.queryForObject(QUERY, new DatabaseProductVersion.DatabaseProductRowMapper())
        );
    }
}
