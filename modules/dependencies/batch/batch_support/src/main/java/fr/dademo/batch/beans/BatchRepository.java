/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.helpers.JobTaskExecutorWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.ExecutionContextSerializer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.Jackson2ExecutionContextStringSerializer;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

import static fr.dademo.batch.beans.BeanValues.*;

/**
 * @author dademo
 */
@Slf4j
@Configuration
@ConditionalOnProperty(
    value = CONFIG_JOBS_REPOSITORY_ENABLED,
    havingValue = "true"
)
public class BatchRepository {

    @SuppressWarnings("unused")
    @Bean(BATCH_DATA_SOURCE_BEAN_NAME)
    public DataSource jobRepositoryDataSource(DataSourcesFactory dataSourcesFactory) {
        return dataSourcesFactory.getDataSource(BATCH_DATA_SOURCE_NAME);
    }

    @SuppressWarnings("unused")
    @Bean(BATCH_DSL_CONTEXT_BEAN_NAME)
    public DSLContext jobRepositoryDslContext(DataSourcesFactory dataSourcesFactory) {
        return dataSourcesFactory.getDslContextByDataSourceName(BATCH_DATA_SOURCE_NAME);
    }

    @SuppressWarnings("unused")
    @Bean(BATCH_DATA_SOURCE_TRANSACTION_MANAGER_BEAN_NAME)
    public PlatformTransactionManager transactionManager(@Qualifier(BATCH_DATA_SOURCE_BEAN_NAME) DataSource jobRepositoryDataSource) {
        return new JdbcTransactionManager(jobRepositoryDataSource);
    }

    @SuppressWarnings("unused")
    @Bean
    public ExecutionContextSerializer executionContextStringSerializer(ObjectMapper objectMapper) {

        final var serializer = new Jackson2ExecutionContextStringSerializer();
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @SuppressWarnings("unused")
    @Bean
    @ConditionalOnMissingBean(JobRepository.class)
    @SneakyThrows
    public JobRepository jdbcJobRepository(@Qualifier(BATCH_DATA_SOURCE_BEAN_NAME) DataSource jobRepositoryDataSource,
                                           @Qualifier(BATCH_DATA_SOURCE_TRANSACTION_MANAGER_BEAN_NAME) PlatformTransactionManager transactionManager,
                                           ExecutionContextSerializer executionContextSerializer) {

        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(jobRepositoryDataSource);
        factory.setTransactionManager(transactionManager);
        factory.setMaxVarCharLength(1000);

        factory.setSerializer(executionContextSerializer);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @SuppressWarnings("unused")
    @Bean
    @ConditionalOnMissingBean(JobExplorer.class)
    @SneakyThrows
    public JobExplorer jobExplorer(@Qualifier(BATCH_DATA_SOURCE_BEAN_NAME) DataSource jobRepositoryDataSource,
                                   ExecutionContextSerializer executionContextSerializer,
                                   PlatformTransactionManager platformTransactionManager) {

        initializeSpringBatchSchema(jobRepositoryDataSource);

        JobExplorerFactoryBean factoryBean = new JobExplorerFactoryBean();
        factoryBean.setDataSource(jobRepositoryDataSource);
        factoryBean.setSerializer(executionContextSerializer);
        factoryBean.setTransactionManager(platformTransactionManager);
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    @SuppressWarnings("unused")
    @Bean
    @ConditionalOnMissingBean(JobLauncher.class)
    @SneakyThrows
    public TaskExecutorJobLauncher jobLauncher(JobRepository jobRepository, @Qualifier(TASK_EXECUTOR_BEAN_NAME) JobTaskExecutorWrapper taskExecutor) {

        final var jobLauncher = new TaskExecutorJobLauncher();

        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(taskExecutor);

        return jobLauncher;
    }

    @SuppressWarnings("unused")
    @Bean
    @ConditionalOnMissingBean(JobRegistry.class)
    @SneakyThrows
    public JobRegistry jobRegistry() {
        return new MapJobRegistry();
    }

    @SuppressWarnings("unused")
    @Bean(TASK_EXECUTOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = TASK_EXECUTOR_BEAN_NAME)
    @SneakyThrows
    public JobTaskExecutorWrapper taskExecutor(BatchConfiguration batchConfiguration) {

        final var poolSize = Math.max(
            Optional.ofNullable(batchConfiguration.getExecutorThreadPoolSize())
                .orElse(DEFAULT_THREAD_POOL_SIZE),
            1
        );

        final var threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.setMaxPoolSize(poolSize);
        // Will cache all incoming jobs
        threadPoolTaskExecutor.setQueueCapacity(Integer.MAX_VALUE);
        threadPoolTaskExecutor.setPrestartAllCoreThreads(true);
        threadPoolTaskExecutor.setDaemon(false);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        threadPoolTaskExecutor.setThreadNamePrefix("job-runner-");
        threadPoolTaskExecutor.initialize();

        return new JobTaskExecutorWrapper(threadPoolTaskExecutor);
    }

    @SuppressWarnings("unused")
    @SneakyThrows
    private void initializeSpringBatchSchema(DataSource jobRepositoryDataSource) {

        boolean isInitialized = false;

        // Trying to figure if the schema have already been initialized
        try (final var jdbcConnection = jobRepositoryDataSource.getConnection()) {
            jdbcConnection.setAutoCommit(false);
            try (final var statement = jdbcConnection.createStatement()) {
                statement.execute("SELECT COUNT(*) FROM public.BATCH_JOB_INSTANCE");
                isInitialized = true;
            } catch (SQLException ex) {
                log.debug("An error occurred when checking for existing schema", ex);
                // Nothing, will be ignored
            } finally {
                jdbcConnection.rollback();
            }
        }

        if (!isInitialized) {

            final var jdbcProperties = new BatchProperties.Jdbc();// Default values
            jdbcProperties.setInitializeSchema(DatabaseInitializationMode.ALWAYS);

            final var initializer = new BatchDataSourceScriptDatabaseInitializer(
                jobRepositoryDataSource, jdbcProperties
            );
            initializer.afterPropertiesSet();
        }
    }
}
