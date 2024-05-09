/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.batch.tools.batch.job.exceptions.MissingJdbcDataSource;
import fr.dademo.batch.tools.batch.job.exceptions.MissingMigrationFolder;
import fr.dademo.batch.tools.batch.job.tasklets.LiquibaseMigrationTasklet;
import fr.dademo.batch.tools.batch.job.tasklets.NoActionTasklet;
import lombok.AccessLevel;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import jakarta.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author dademo
 */
@Getter(AccessLevel.PROTECTED)
public abstract class BaseChunkedJob extends BaseSteppedJob {

    private final StepBuilderFactory stepBuilderFactory;

    private final BatchConfiguration batchConfiguration;

    private final BatchDataSourcesConfiguration batchDataSourcesConfiguration;

    private final DataSourcesFactory dataSourcesFactory;

    private final ResourceLoader resourceLoader;

    protected BaseChunkedJob(JobBuilderFactory jobBuilderFactory,
                             StepBuilderFactory stepBuilderFactory,
                             BatchConfiguration batchConfiguration,
                             BatchDataSourcesConfiguration batchDataSourcesConfiguration,
                             DataSourcesFactory dataSourcesFactory,
                             ResourceLoader resourceLoader) {

        super(jobBuilderFactory);
        this.stepBuilderFactory = stepBuilderFactory;
        this.batchConfiguration = batchConfiguration;
        this.batchDataSourcesConfiguration = batchDataSourcesConfiguration;
        this.dataSourcesFactory = dataSourcesFactory;
        this.resourceLoader = resourceLoader;
    }

    protected abstract ChunkedStepProvider getChunkedStepProvider();

    @Nonnull
    protected abstract BatchConfiguration.JobConfiguration getJobConfiguration();

    // ---
    // Extended getters
    // ---

    @Nullable
    protected String getLiquibaseMigrationFolder() {
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

    @SuppressWarnings("unused")
    @Nonnull
    @NotBlank
    protected String getJobInputDataSourceSchema() {
        return getDataSourceSchema(getJobInputDataSourceName());
    }

    @SuppressWarnings("unused")
    @Nonnull
    @NotBlank
    protected String getJobOutputDataSourceSchema() {
        return getDataSourceSchema(getJobOutputDataSourceName());
    }

    @SuppressWarnings("unused")
    protected DSLContext getJobInputDslContext() {
        return dataSourcesFactory.getDslContextByDataSourceName(getJobInputDataSourceName());
    }

    @SuppressWarnings("unused")
    protected DSLContext getJobOutputDslContext() {
        return dataSourcesFactory.getDslContextByDataSourceName(getJobOutputDataSourceName());
    }

    @Nonnull
    @NotBlank
    protected String getJobInputDataSourceName() {
        return getDataSourceName(getJobConfiguration().getInputDataSource());
    }

    @Nonnull
    @NotBlank
    private String getJobOutputDataSourceName() {
        return getDataSourceName(getJobConfiguration().getOutputDataSource());
    }

    @Nonnull
    protected List<Tasklet> getInitTasks() {
        return Collections.singletonList(getJobExecutionGuardTasklet());
    }

    @Nonnull
    protected List<Tasklet> getClosingTasks() {
        return Collections.singletonList(getJobClosingTasklet());
    }

    @Nonnull
    protected List<ChunkListener> getChunkListeners() {
        return Collections.emptyList();
    }

    @Override
    public boolean isJobAvailable() {
        return Optional.ofNullable(getJobConfiguration().getEnabled())
            .orElseGet(BatchConfiguration.JobConfiguration::getDefaultIsEnabled);
    }

    private List<Step> getInitSteps(String jobName) {

        final var tasks = getInitTasks();
        if (!tasks.isEmpty()) {
            return IntStream.range(0, tasks.size())
                .mapToObj(stepIndex -> getStandardStep(
                    tasks.get(stepIndex),
                    String.format("%s-init-%d", jobName, stepIndex + 1))
                )
                .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private List<Step> getClosingSteps(String jobName) {

        final var tasks = getClosingTasks();
        if (!tasks.isEmpty()) {
            return IntStream.range(0, tasks.size())
                .mapToObj(stepIndex -> getStandardStep(
                    tasks.get(stepIndex),
                    String.format("%s-closing-%d", jobName, stepIndex + 1))
                )
                .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    // ---
    // Internal functions
    // ---

    @Override
    protected final List<Step> getJobSteps() {

        final var jobName = getJobName();

        return Stream.concat(
            Stream.concat(
                getInitSteps(jobName).stream(),
                Stream.of(getChunkedStepProvider().getStep(jobName, getJobConfiguration(), getChunkListeners()))
            ),
            getClosingSteps(jobName).stream()
        ).collect(Collectors.toList());
    }

    protected Tasklet getJobExecutionGuardTasklet() {
        return new NoActionTasklet();
    }

    protected Tasklet getJobClosingTasklet() {
        return new NoActionTasklet();
    }

    // ---
    // Job builder helpers
    // ---

    @SuppressWarnings("unused")
    @Nonnull
    protected Tasklet getLiquibaseInputMigrationTasklet() {

        return Optional.ofNullable(getJobConfiguration().getInputDataSource())
            .map(this::getLiquibaseMigrationTasklet)
            .orElseThrow(() -> MissingJdbcDataSource.missingInputJdbcDataSource(getClass()));
    }

    @SuppressWarnings("unused")
    @Nonnull
    protected Tasklet getLiquibaseOutputMigrationTasklet() {

        return Optional.ofNullable(getJobConfiguration().getOutputDataSource())
            .map(this::getLiquibaseMigrationTasklet)
            .orElseThrow(() -> MissingJdbcDataSource.missingOutputJdbcDataSource(getClass()));
    }

    protected Step getStandardStep(Tasklet tasklet, String name) {

        final var stepBuilder = stepBuilderFactory
            .get(name)
            .tasklet(tasklet)
            .startLimit(1)
            .throttleLimit(1);
        getStepExecutionListeners().forEach(stepBuilder::listener);
        return stepBuilder.build();
    }

    @Nonnull
    private Tasklet getLiquibaseMigrationTasklet(BatchConfiguration.JobDataSourceConfiguration jobDataSourceConfiguration) {

        final var dataSourceName = jobDataSourceConfiguration.getName();
        final var dataSourceConfiguration = batchDataSourcesConfiguration
            .getJDBCDataSourceConfigurationByName(dataSourceName);

        return LiquibaseMigrationTasklet.builder()
            .migrationFolder(
                Optional.ofNullable(getLiquibaseMigrationFolder())
                    .orElseThrow(() -> new MissingMigrationFolder(getClass()))
            )
            .jobDataSourceConfiguration(jobDataSourceConfiguration)
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

    private String getDataSourceSchema(String dataSourceName) {

        return batchDataSourcesConfiguration
            .getJDBCDataSourceConfigurationByName(dataSourceName)
            .getSchema();
    }

    private String getDataSourceName(@Nullable BatchConfiguration.JobDataSourceConfiguration jobDataSourceConfiguration) {

        return Optional.ofNullable(jobDataSourceConfiguration)
            .map(BatchConfiguration.JobDataSourceConfiguration::getName)
            .orElseThrow(MissingJobDataSourceConfigurationException.forJob(getJobName()));
    }
}
