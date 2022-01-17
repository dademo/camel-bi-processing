/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task;

import fr.dademo.supervision.entities.FlywayMigrationsTools;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author dademo
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class Beans {

    public static final String TASK_DATASOURCE_BEAN_NAME = "TASK_DATASOURCE_BEAN";

    @Bean(name = TASK_DATASOURCE_BEAN_NAME)
    public DataSource dataSource() {

        final var builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    }

    @Bean
    public Flyway getMigrations(@Qualifier(TASK_DATASOURCE_BEAN_NAME) DataSource dataSource) {
        return FlywayMigrationsTools.prepareFlywayMigration(dataSource);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        @Qualifier(TASK_DATASOURCE_BEAN_NAME) DataSource dataSource) {

        final var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        final var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("fr.dademo.supervision");
        factory.setDataSource(dataSource);

        return factory;
    }

    @Bean
    public TaskConfigurer taskConfigurer(@Qualifier(TASK_DATASOURCE_BEAN_NAME) DataSource taskDataSource) {
        return new DefaultTaskConfigurer(taskDataSource);
    }
}
