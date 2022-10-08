/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.services.DataSetService;
import fr.dademo.batch.tools.batch.job.listeners.DataSetBatchStatusUpdateJobExecutionListener;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dademo
 */
public abstract class BaseDataSetChunkedJob extends BaseChunkedJob {

    private final DataSetService dataSetService;

    protected BaseDataSetChunkedJob(JobBuilderFactory jobBuilderFactory,
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
            resourceLoader
        );
        this.dataSetService = dataSetService;
    }

    protected abstract Tasklet getJobDataSetResourceDefinitionTask();

    @Nonnull
    @Override
    protected List<Tasklet> getInitTasks() {
        return Arrays.asList(
            getJobDataSetResourceDefinitionTask(),
            getJobExecutionGuardTasklet()
        );
    }

    @Nonnull
    @Override
    protected List<JobExecutionListener> getJobExecutionListeners() {

        return Stream.concat(
            super.getJobExecutionListeners().stream(),
            Stream.of(new DataSetBatchStatusUpdateJobExecutionListener(dataSetService))
        ).collect(Collectors.toList());
    }
}
