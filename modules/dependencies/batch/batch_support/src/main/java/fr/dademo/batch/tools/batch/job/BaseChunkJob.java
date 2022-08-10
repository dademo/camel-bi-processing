/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.tools.batch.job.exceptions.MissingJdbcDataSource;
import fr.dademo.batch.tools.batch.job.exceptions.MissingMigrationFolder;
import fr.dademo.batch.tools.batch.job.listeners.DefaultJobExecutionListener;
import fr.dademo.batch.tools.batch.job.listeners.DefaultStepExecutionListener;
import fr.dademo.batch.tools.batch.job.listeners.StepThreadPoolListener;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dademo
 */
public abstract class BaseChunkJob<I, O> implements BatchJobProvider {

    public static final int MAX_THREAD_POOL_QUEUE_SIZE_FACTOR = 5;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BatchDataSourcesConfiguration batchDataSourcesConfiguration;

    @Autowired
    private DataSourcesFactory dataSourcesFactory;

    @Autowired
    private ResourceLoader resourceLoader;


    @Nonnull
    protected abstract BatchConfiguration.JobConfiguration getJobConfiguration();

    @Nonnull
    protected abstract String getJobName();

    @Nullable
    protected String getDefaultJdbcDataSourceName() {
        return null;
    }

    @Nullable
    protected String getMigrationFolder() {
        return null;
    }

    @Nullable
    protected String getDefaultDatabaseSchema() {
        return null;
    }

    @Nullable
    protected String getDefaultDatabaseCatalog() {
        return null;
    }

    @Nullable
    protected Tasklet getInitTask() {
        return null;
    }

    protected Tasklet getLiquibaseMigrationTasklet() {

        final var dataSourceName = Optional.ofNullable(getJobConfiguration().getDataSourceName())
            .orElseGet(
                () -> Optional.ofNullable(getDefaultJdbcDataSourceName())
                    .orElseThrow(() -> new MissingJdbcDataSource(getClass()))
            );
        final var dataSourceConfiguration = batchDataSourcesConfiguration.getJDBCDataSourceConfigurationByName(dataSourceName);

        return LiquibaseMigrationTasklet.builder()
            .migrationFolder(
                Optional.ofNullable(getMigrationFolder())
                    .orElseThrow(() -> new MissingMigrationFolder(getClass()))
            )
            .jobConfiguration(getJobConfiguration())
            .databaseCatalog(
                Optional.ofNullable(dataSourceConfiguration.getCatalog())
                    .orElseGet(this::getDefaultDatabaseCatalog)
            )
            .databaseSchema(
                Optional.ofNullable(dataSourceConfiguration.getSchema())
                    .orElseGet(this::getDefaultDatabaseSchema)
            )
            .dataSource(dataSourcesFactory.getDataSource(dataSourceName))
            .resourceLoader(resourceLoader)
            .build();
    }

    @Nonnull
    protected abstract ItemReader<I> getItemReader();

    @Nonnull
    protected abstract ItemProcessor<I, O> getItemProcessor();

    @Nonnull
    protected abstract ItemWriter<O> getItemWriter();

    @Nonnull
    protected List<JobExecutionListener> getJobExecutionListeners() {
        return Collections.singletonList(new DefaultJobExecutionListener());
    }

    protected List<StepExecutionListener> getStepExecutionListeners() {
        return Collections.singletonList(new DefaultStepExecutionListener());
    }

    protected List<ChunkListener> getChunkListeners() {
        return Collections.emptyList();
    }

    @Nonnull
    private List<JobExecutionListener> getAllJobExecutionListener() {
        return getJobExecutionListeners();
    }

    private List<StepExecutionListener> getAllStepExecutionListeners(ThreadPoolTaskExecutor threadPoolTaskExecutor) {

        return Stream.concat(
            Stream.of(new StepThreadPoolListener(threadPoolTaskExecutor)),
            getStepExecutionListeners().stream()
        ).collect(Collectors.toList());
    }

    protected List<ChunkListener> getAllChunkListeners() {
        return getChunkListeners();
    }


    @Override
    public Job getJob() {

        if (Boolean.TRUE.equals(
            Optional.ofNullable(getJobConfiguration().getEnabled())
                .orElseGet(BatchConfiguration.JobConfiguration::getDefaultIsEnabled))) {

            final var jobName = getJobName();
            final var jobProcessStep = getJobStep(jobName);

            final var jobBuilder = jobBuilderFactory
                .get(jobName)
                .incrementer(new RunIdIncrementer())
                .preventRestart();

            final var initStep = getInitStep(jobName);
            final var jobStep = getJobStep(jobName);

            final var stepJobBuilder = initStep.map(
                step -> jobBuilder
                    .start(step)
                    .next(jobStep)
            ).orElseGet(() -> jobBuilder.start(jobStep));

            getAllJobExecutionListener().forEach(stepJobBuilder::listener);

            return stepJobBuilder.build();
        } else {
            return null;
        }
    }

    private Optional<Step> getInitStep(String jobName) {

        return Optional.ofNullable(getInitTask())
            .map(
                initTask -> stepBuilderFactory
                    .get(jobName)
                    .tasklet(initTask)
                    .build()
            );
    }

    private Step getJobStep(String jobName) {

        final var threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        final int poolSize = Optional.ofNullable(getJobConfiguration().getMaxThreads())
            .orElseGet(BatchConfiguration.JobConfiguration::getDefaultMaxThreads);

        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.setMaxPoolSize(poolSize);
        threadPoolTaskExecutor.setQueueCapacity(poolSize * MAX_THREAD_POOL_QUEUE_SIZE_FACTOR);

        threadPoolTaskExecutor.setPrestartAllCoreThreads(true);
        threadPoolTaskExecutor.setDaemon(false);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setThreadNamePrefix(String.format("bi-job-%s-", jobName));

        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        final var jobStep = stepBuilderFactory
            .get(jobName)
            .<I, O>chunk(
                Optional.ofNullable(getJobConfiguration().getChunkSize())
                    .orElseGet(BatchConfiguration.JobConfiguration::getDefaultChunkSize))
            .reader(getItemReader())
            .processor(getItemProcessor())
            .writer(getItemWriter())
            .taskExecutor(threadPoolTaskExecutor)
            .throttleLimit(poolSize);   // Our thread pool size;

        getAllStepExecutionListeners(threadPoolTaskExecutor).forEach(jobStep::listener);
        getAllChunkListeners().forEach(jobStep::listener);

        return jobStep.build();
    }
}
