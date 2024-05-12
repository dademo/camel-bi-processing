/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job.listeners;

import fr.dademo.batch.services.DataSetService;
import fr.dademo.batch.services.dto.DataSetDto;
import fr.dademo.batch.tools.batch.reader.exceptions.MissingContextDataSetResource;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.util.Optional;

import static fr.dademo.batch.tools.batch.job.BatchSharedValues.KEY_BATCH_EXECUTION_CONTEXT_DATASET_DTO;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@AllArgsConstructor
public class DataSetBatchStatusUpdateJobExecutionListener implements JobExecutionListener {

    private final DataSetService dataSetService;

    @Override
    public void beforeJob(@Nonnull JobExecution jobExecution) {
        // Nothing to do, data set information will be queried in the beginning of the batch
    }

    @Override
    public void afterJob(@Nonnull JobExecution jobExecution) {

        // Resource not available when stopped
        if (jobExecution.getExitStatus().compareTo(ExitStatus.STOPPED) != 0) {
            // The job has successfully completed or has failed
            final var dataSetDto = getDataSetDto(jobExecution);

            log.info("Persisting the data set state");
            if (dataSetDto.getState() != DataSetDto.DataSetDtoState.READY) {
                dataSetDto.setState(
                    hasJobSucceeded(jobExecution) ? DataSetDto.DataSetDtoState.READY : DataSetDto.DataSetDtoState.FAILED
                );
                dataSetService.updateDataSet(dataSetDto);
            }
        }
    }

    private DataSetDto getDataSetDto(JobExecution jobExecution) {

        return (DataSetDto) Optional.ofNullable(
                jobExecution
                    .getExecutionContext()
                    .get(KEY_BATCH_EXECUTION_CONTEXT_DATASET_DTO)
            )
            .orElseThrow(MissingContextDataSetResource::new);
    }

    private boolean hasJobSucceeded(JobExecution jobExecution) {
        return jobExecution.getExitStatus().compareTo(ExitStatus.COMPLETED) == 0;
    }
}
