/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author dademo
 */
@Configuration
public class ModuleBeans {

    public static final String MODULE_DATASOURCE_BEAN_NAME = "POSTGRESQL_DATASOURCE_MODULE_NAME";
    public static final String MODULE_JDBC_TEMPLATE_BEAN_NAME = "POSTGRESQL_JDBC_TEMPLATE_MODULE_NAME";
    public static final String DRIVER_CLASS_POSTGRESQL = "org.postgresql.Driver";

    @Bean(name = MODULE_DATASOURCE_BEAN_NAME)
    public DataSource moduleDataSource(ModuleConfiguration moduleConfiguration) {

        final var dataSourceConfiguration = moduleConfiguration.getDatasource();
        final var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(DRIVER_CLASS_POSTGRESQL);
        dataSourceBuilder.url(dataSourceConfiguration.getUrl());
        dataSourceBuilder.username(dataSourceConfiguration.getUsername());
        dataSourceBuilder.password(dataSourceConfiguration.getPassword());
        return dataSourceBuilder.build();
    }

    @Bean(name = MODULE_JDBC_TEMPLATE_BEAN_NAME)
    public JdbcTemplate moduleJdbcTemplate(@Qualifier(MODULE_DATASOURCE_BEAN_NAME) DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
