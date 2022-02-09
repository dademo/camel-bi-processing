/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.dademo.supervision.dependencies.persistence.configuration.PersistenceConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;
import java.util.Properties;

import static fr.dademo.supervision.dependencies.persistence.PersistenceBeans.TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN_NAME;

/**
 * @author dademo
 */
@Configuration
//@EntityScan(basePackageClasses = DataBackendStateExecutionEntity.class)
@EnableJpaRepositories(
    basePackages = "fr.dademo.supervision",
    entityManagerFactoryRef = TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN_NAME,
    transactionManagerRef = PersistenceBeans.PERSISTENCE_TRANSACTION_MANAGER_BEAN_NAME
)
@EnableTransactionManagement
public class PersistenceBeans {

    public static final String PERSISTENCE_DATASOURCE_BEAN_NAME = "PERSISTENCE_DATASOURCE_BEAN";
    public static final String JPA_PROPERTIES_BEAN_NAME = "JPA_PROPERTIES_BEAN";
    public static final String TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN_NAME = "TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN";
    public static final String PERSISTENCE_TRANSACTION_MANAGER_BEAN_NAME = "TASK_TRANSACTION_MANAGER_BEAN";

    @Bean(name = PERSISTENCE_DATASOURCE_BEAN_NAME)
    public DataSource dataSource(PersistenceConfiguration persistenceConfiguration) {

        final var hikariConfiguration = new HikariConfig();

        hikariConfiguration.setJdbcUrl(persistenceConfiguration.getDatasource().getUrl());
        hikariConfiguration.setUsername(persistenceConfiguration.getDatasource().getUsername());
        hikariConfiguration.setPassword(persistenceConfiguration.getDatasource().getPassword());
        hikariConfiguration.setMinimumIdle(persistenceConfiguration.getMinimumIdle());
        hikariConfiguration.setMaximumPoolSize(persistenceConfiguration.getMaximumPoolSize());
        hikariConfiguration.setAutoCommit(false);

        return new HikariDataSource(hikariConfiguration);
    }

    @Profile("!dev")
    @Bean(name = JPA_PROPERTIES_BEAN_NAME)
    public Properties jpaPropertiesDefault(PersistenceConfiguration persistenceConfiguration) {

        final var jpaProperties = new Properties();

        jpaProperties.setProperty("hibernate.default_schema", persistenceConfiguration.getDatasource().getSchema());
        jpaProperties.setProperty("hibernate.javax.cache.missing_cache_strategy", "create");
        jpaProperties.setProperty("hibernate.format_sql", Boolean.FALSE.toString());
        jpaProperties.setProperty("hibernate.jdbc.batch_size", String.valueOf(persistenceConfiguration.getBatchSize()));

        if (persistenceConfiguration.isCacheEnabled()) {
            jpaProperties.setProperty("hibernate.cache.use_second_level_cache", Boolean.TRUE.toString());
            jpaProperties.setProperty("hibernate.cache.use_query_cache", Boolean.TRUE.toString());
            jpaProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.jcache.JCacheRegionFactory");
        }

        return jpaProperties;
    }

    @Profile("dev")
    @Bean(name = JPA_PROPERTIES_BEAN_NAME)
    public Properties jpaPropertiesDev(PersistenceConfiguration persistenceConfiguration) {

        final var jpaProperties = new Properties();

        jpaProperties.setProperty("hibernate.default_schema", persistenceConfiguration.getDatasource().getSchema());
        jpaProperties.setProperty("hibernate.javax.cache.missing_cache_strategy", "create");
        jpaProperties.setProperty("hibernate.format_sql", Boolean.FALSE.toString());
        jpaProperties.setProperty("hibernate.jdbc.batch_size", String.valueOf(persistenceConfiguration.getBatchSize()));
        jpaProperties.setProperty("hibernate.generate_statistics", Boolean.TRUE.toString());

        if (persistenceConfiguration.isCacheEnabled()) {
            jpaProperties.setProperty("hibernate.cache.use_second_level_cache", Boolean.TRUE.toString());
            jpaProperties.setProperty("hibernate.cache.use_query_cache", Boolean.TRUE.toString());
            jpaProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.jcache.JCacheRegionFactory");
        }

        return jpaProperties;
    }

    @Bean(name = TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN_NAME)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        @Qualifier(PERSISTENCE_DATASOURCE_BEAN_NAME) DataSource dataSource,
        @Qualifier(JPA_PROPERTIES_BEAN_NAME) Properties jpaProperties,
        PersistenceConfiguration persistenceConfiguration) {

        final var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        final var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("fr.dademo.supervision");
        factory.setDataSource(dataSource);
        factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);

        jpaProperties.putAll(factory.getJpaPropertyMap());
        factory.setJpaProperties(jpaProperties);

        return factory;
    }

    @Bean(name = PERSISTENCE_TRANSACTION_MANAGER_BEAN_NAME)
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
        return transactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate(@Qualifier(PERSISTENCE_TRANSACTION_MANAGER_BEAN_NAME) PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }
}
