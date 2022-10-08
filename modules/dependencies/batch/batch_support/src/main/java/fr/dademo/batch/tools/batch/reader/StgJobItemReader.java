/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.reader;

import fr.dademo.batch.tools.batch.reader.exceptions.MissingContextDataSetResource;
import fr.dademo.data.definitions.DataSetResource;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import javax.annotation.Nonnull;
import java.util.Optional;

import static fr.dademo.batch.tools.batch.job.BatchSharedValues.KEY_BATCH_EXECUTION_CONTEXT_DATASET_DEFINITION;

/**
 * @author dademo
 */
public abstract class StgJobItemReader<T> extends UnidirectionalItemStreamReaderSupport<T> implements StepExecutionListener {

    @Getter(AccessLevel.PROTECTED)
    private DataSetResource dataSetResource;

    @Override
    public void beforeStep(@Nonnull StepExecution stepExecution) {

        dataSetResource = (DataSetResource) Optional.ofNullable(
                stepExecution
                    .getJobExecution()
                    .getExecutionContext()
                    .get(KEY_BATCH_EXECUTION_CONTEXT_DATASET_DEFINITION)
            )
            .orElseThrow(MissingContextDataSetResource::new);
    }

    @Override
    public ExitStatus afterStep(@Nonnull StepExecution stepExecution) {
        return ExitStatus.COMPLETED; // Nothing
    }
}
