/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.job.task.standalone;

import fr.dademo.supervision.job.task.standalone.configuration.TaskConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author dademo
 */
@Configuration
public class TaskBeans {

    public static final String TASK_DATASOURCE_BEAN_NAME = "TASK_DATASOURCE_BEAN";

    @Bean(name = TASK_DATASOURCE_BEAN_NAME)
    public DataSource dataSource(TaskConfiguration taskConfiguration) {

        final var datasourceConfiguration = taskConfiguration.getDatasource();
        final var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(datasourceConfiguration.getUrl());
        dataSourceBuilder.username(datasourceConfiguration.getUsername());
        dataSourceBuilder.password(datasourceConfiguration.getPassword());
        return dataSourceBuilder.build();
    }

    @Bean
    public TaskConfigurer taskConfigurer(@Qualifier(TASK_DATASOURCE_BEAN_NAME) DataSource taskDataSource) {
        return new DefaultTaskConfigurer(taskDataSource);
    }
}
