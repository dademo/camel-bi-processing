/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.job.task.light;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import fr.dademo.supervision.job.task.light.configuration.TaskConfiguration;
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

    public static final String TASK_DATA_SOURCE_BEAN_NAME = "TASK_DATA_SOURCE_BEAN";
    public static final String OUTPUT_DATA_SOURCE_NAME = "output";

    @Bean(name = TASK_DATA_SOURCE_BEAN_NAME)
    public DataSource dataSource(TaskConfiguration taskConfiguration) {

        final var datasourceConfiguration = taskConfiguration.getDatasource();
        final var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(datasourceConfiguration.getUrl());
        dataSourceBuilder.username(datasourceConfiguration.getUsername());
        dataSourceBuilder.password(datasourceConfiguration.getPassword());
        return dataSourceBuilder.build();
    }

    @Bean
    public TaskConfigurer taskConfigurer(@Qualifier(TASK_DATA_SOURCE_BEAN_NAME) DataSource taskDataSource) {
        return new DefaultTaskConfigurer(taskDataSource);
    }

    @Bean
    public ObjectMapper objectMapper() {

        final var objectMapper = new ObjectMapper();

        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));

        return objectMapper;
    }
}
