/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task;

import fr.dademo.supervision.task.configuration.TaskConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

import static fr.dademo.supervision.task.Beans.TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN_NAME;

/**
 * @author dademo
 */
@Configuration
//@EntityScan(basePackageClasses = DataBackendStateExecutionEntity.class)
@EnableJpaRepositories(
    basePackages = "fr.dademo.supervision",
    entityManagerFactoryRef = TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN_NAME,
    transactionManagerRef = Beans.TASK_TRANSACTION_MANAGER_BEAN_NAME
)
@EnableTransactionManagement
public class Beans {

    public static final String TASK_DATASOURCE_BEAN_NAME = "TASK_DATASOURCE_BEAN";
    public static final String TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN_NAME = "TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN";
    public static final String TASK_TRANSACTION_MANAGER_BEAN_NAME = "TASK_TRANSACTION_MANAGER_BEAN";

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

    @Bean(name = TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN_NAME)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        @Qualifier(TASK_DATASOURCE_BEAN_NAME) DataSource dataSource,
        TaskConfiguration taskConfiguration) {

        final var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        final var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("fr.dademo.supervision");
        factory.setDataSource(dataSource);

        var jpaProperties = new Properties();
        jpaProperties.putAll(factory.getJpaPropertyMap());
        jpaProperties.setProperty("hibernate.default_schema", taskConfiguration.getDatasource().getSchema());
        factory.setJpaProperties(jpaProperties);

        return factory;
    }

    @Bean(name = TASK_TRANSACTION_MANAGER_BEAN_NAME)
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
        return transactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate(@Qualifier(TASK_TRANSACTION_MANAGER_BEAN_NAME) PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }
}
