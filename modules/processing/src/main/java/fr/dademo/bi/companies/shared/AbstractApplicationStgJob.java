/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.shared;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.services.DataSetService;
import fr.dademo.batch.tools.batch.job.BaseStgJob;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dademo
 */
public abstract class AbstractApplicationStgJob extends BaseStgJob {

    protected AbstractApplicationStgJob(JobBuilderFactory jobBuilderFactory,
                                        StepBuilderFactory stepBuilderFactory,
                                        BatchConfiguration batchConfiguration,
                                        BatchDataSourcesConfiguration batchDataSourcesConfiguration,
                                        DataSourcesFactory dataSourcesFactory,
                                        ResourceLoader resourceLoader,
                                        DataSetService dataSetService) {
        super(
            jobBuilderFactory,
            stepBuilderFactory,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader,
            dataSetService
        );
    }

    protected abstract Tasklet getJooqTruncateTasklet();

    @SuppressWarnings("java:S1452")
    @Nonnull
    protected abstract ItemWriter<?> getItemWriter();

    @Nonnull
    @Override
    protected List<Tasklet> getInitTasks() {

        final var parentInitTasks = super.getInitTasks();
        if (AbstractApplicationJdbcWriter.class.isAssignableFrom(getItemWriter().getClass())) {
            return Stream.concat(
                parentInitTasks.stream(),
                Stream.of(
                    getLiquibaseOutputMigrationTasklet(),
                    getJooqTruncateTasklet()
                )
            ).collect(Collectors.toList());
        } else {
            return parentInitTasks;
        }
    }
}
