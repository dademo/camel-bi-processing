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

package fr.dademo.supervision.dependencies.persistence;

import fr.dademo.supervision.dependencies.persistence.configuration.PersistenceConfiguration;
import fr.dademo.supervision.dependencies.persistence.repositories.database.caching.EntityClassKeyGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
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
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

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
@EnableCaching
public class PersistenceBeans {

    public static final String PERSISTENCE_DATASOURCE_BEAN_NAME = "PERSISTENCE_DATASOURCE_BEAN_NAME";
    public static final String TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN_NAME = "TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN";
    public static final String PERSISTENCE_TRANSACTION_MANAGER_BEAN_NAME = "TASK_TRANSACTION_MANAGER_BEAN";
    public static final String TASK_ENTITY_CLASS_KEY_GENERATOR_BEAN_NAME = "TASK_ENTITY_CLASS_KEY_GENERATOR_BEAN_NAME";

    public static final String CACHE_DATA_BACKEND_DATABASE_REPOSITORY = "DataBackendDatabaseRepository";
    public static final String CACHE_DATA_BACKEND_DATABASE_SCHEMA_INDEX_REPOSITORY = "DataBackendDatabaseSchemaIndexRepository";
    public static final String CACHE_DATA_BACKEND_DATABASE_SCHEMA_REPOSITORY = "DataBackendDatabaseSchemaRepository";
    public static final String CACHE_DATA_BACKEND_DATABASE_SCHEMA_TABLE_REPOSITORY = "DataBackendDatabaseSchemaTableRepository";
    public static final String CACHE_DATA_BACKEND_DATABASE_SCHEMA_VIEW_REPOSITORY = "DataBackendDatabaseSchemaViewRepository";
    public static final String CACHE_DATA_BACKEND_GLOBAL_DATABASE_REPOSITORY = "DataBackendGlobalDatabaseRepository";
    public static final String CACHE_DATA_BACKEND_DESCRIPTION_REPOSITORY = "DataBackendDescriptionRepository";
    public static final String CACHE_DATA_BACKEND_MODULE_META_DATA_REPOSITORY = "DataBackendModuleMetaDataRepository";
    private static final String[] CACHE_ALL = {
        CACHE_DATA_BACKEND_DATABASE_REPOSITORY,
        CACHE_DATA_BACKEND_DATABASE_SCHEMA_INDEX_REPOSITORY,
        CACHE_DATA_BACKEND_DATABASE_SCHEMA_REPOSITORY,
        CACHE_DATA_BACKEND_DATABASE_SCHEMA_TABLE_REPOSITORY,
        CACHE_DATA_BACKEND_DATABASE_SCHEMA_VIEW_REPOSITORY,
        CACHE_DATA_BACKEND_GLOBAL_DATABASE_REPOSITORY,
        CACHE_DATA_BACKEND_DESCRIPTION_REPOSITORY,
        CACHE_DATA_BACKEND_MODULE_META_DATA_REPOSITORY,
    };

    @Bean(name = PERSISTENCE_DATASOURCE_BEAN_NAME)
    public DataSource dataSource(PersistenceConfiguration persistenceConfiguration) {

        final var datasourceConfiguration = persistenceConfiguration.getDatasource();
        final var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(datasourceConfiguration.getUrl());
        dataSourceBuilder.username(datasourceConfiguration.getUsername());
        dataSourceBuilder.password(datasourceConfiguration.getPassword());
        return dataSourceBuilder.build();
    }

    @Bean(name = TASK_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN_NAME)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        @Qualifier(PERSISTENCE_DATASOURCE_BEAN_NAME) DataSource dataSource,
        PersistenceConfiguration persistenceConfiguration) {

        final var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        final var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("fr.dademo.supervision");
        factory.setDataSource(dataSource);

        var jpaProperties = new Properties();
        jpaProperties.putAll(factory.getJpaPropertyMap());
        jpaProperties.setProperty("hibernate.default_schema", persistenceConfiguration.getDatasource().getSchema());
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

    @Bean
    public CacheManager cacheManager() {

        final var cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(
            Arrays.stream(CACHE_ALL)
                .map(cacheName -> new ConcurrentMapCache(cacheName, false))
                .collect(Collectors.toList())
        );
        return cacheManager;
    }

    @Bean(TASK_ENTITY_CLASS_KEY_GENERATOR_BEAN_NAME)
    public EntityClassKeyGenerator entityClassKeyGenerator() {
        return new EntityClassKeyGenerator();
    }
}
