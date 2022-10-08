/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job.tasklets;

import fr.dademo.batch.services.dto.DataSetDto;
import fr.dademo.batch.tools.batch.job.exceptions.MissingContextDataSetResource;
import fr.dademo.data.definitions.DataSetResource;
import org.springframework.batch.core.StepContribution;

import java.util.Optional;

import static fr.dademo.batch.tools.batch.job.BatchSharedValues.KEY_BATCH_EXECUTION_CONTEXT_DATASET_DEFINITION;
import static fr.dademo.batch.tools.batch.job.BatchSharedValues.KEY_BATCH_EXECUTION_CONTEXT_DATASET_DTO;

/**
 * @author dademo
 */
public abstract class BaseDataSetTasklet {

    protected DataSetResource getDataSetResourceFromContext(StepContribution stepContribution) {

        return (DataSetResource) Optional.ofNullable(
                stepContribution.getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .get(KEY_BATCH_EXECUTION_CONTEXT_DATASET_DEFINITION)
            )
            .orElseThrow(MissingContextDataSetResource::new);
    }

    protected DataSetDto getDataSetDtoFromContext(StepContribution stepContribution) {

        return (DataSetDto) Optional.ofNullable(
                stepContribution.getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .get(KEY_BATCH_EXECUTION_CONTEXT_DATASET_DTO)
            )
            .orElseThrow(MissingContextDataSetResource::new);
    }
}
