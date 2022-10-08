/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job.tasklets;

import fr.dademo.data.definitions.DataSetResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import javax.annotation.Nonnull;

import static fr.dademo.batch.tools.batch.job.BatchSharedValues.KEY_BATCH_EXECUTION_CONTEXT_DATASET_DEFINITION;

/**
 * @author dademo
 */
@Slf4j
public class DataSetResourceQueryTasklet implements Tasklet {

    private final DataSetResourceProvider dataSetResourceProvider;

    public DataSetResourceQueryTasklet(DataSetResourceProvider dataSetResourceProvider) {
        this.dataSetResourceProvider = dataSetResourceProvider;
    }

    @Override
    public RepeatStatus execute(@Nonnull StepContribution contribution,
                                @Nonnull ChunkContext chunkContext) {

        log.info("Getting dataset definition");
        final var dataSetResource = dataSetResourceProvider.getResource();

        // Pushing to the batch context
        contribution.getStepExecution()
            .getExecutionContext()
            .put(KEY_BATCH_EXECUTION_CONTEXT_DATASET_DEFINITION, dataSetResource);

        return RepeatStatus.FINISHED;
    }

    @FunctionalInterface
    public interface DataSetResourceProvider {
        @Nonnull
        DataSetResource getResource();
    }
}
