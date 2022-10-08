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

package fr.dademo.batch.tools.batch.job;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.services.DataSetService;
import fr.dademo.batch.tools.batch.job.tasklets.DataSetBatchStartupGuardTasklet;
import fr.dademo.batch.tools.batch.job.tasklets.DataSetResourceQueryTasklet;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.dademo.batch.tools.batch.job.BatchSharedValues.KEY_BATCH_EXECUTION_CONTEXT_DATASET_DEFINITION;
import static fr.dademo.batch.tools.batch.job.BatchSharedValues.KEY_BATCH_EXECUTION_CONTEXT_DATASET_DTO;

public abstract class BaseStgJob extends BaseDataSetChunkedJob {

    private final DataSetService dataSetService;

    protected BaseStgJob(JobBuilderFactory jobBuilderFactory,
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
        this.dataSetService = dataSetService;
    }

    protected abstract DataSetResourceQueryTasklet.DataSetResourceProvider getDataSetResourceProvider();

    protected Tasklet getJobDataSetResourceDefinitionTask() {
        return new DataSetResourceQueryTasklet(getDataSetResourceProvider());
    }

    @Override
    protected Tasklet getJobExecutionGuardTasklet() {
        return new DataSetBatchStartupGuardTasklet(dataSetService);
    }

    @Nonnull
    @Override
    protected List<StepExecutionListener> getStepExecutionListeners() {
        return Stream.concat(
            super.getStepExecutionListeners().stream(),
            Stream.of(executionContextPromotionListener())
        ).collect(Collectors.toList());
    }

    @Nonnull
    protected ExecutionContextPromotionListener executionContextPromotionListener() {

        final var listener = new ExecutionContextPromotionListener();

        listener.setKeys(new String[]{
            KEY_BATCH_EXECUTION_CONTEXT_DATASET_DEFINITION,
            KEY_BATCH_EXECUTION_CONTEXT_DATASET_DTO,
        });
        listener.setStrict(false);

        return listener;
    }
}
