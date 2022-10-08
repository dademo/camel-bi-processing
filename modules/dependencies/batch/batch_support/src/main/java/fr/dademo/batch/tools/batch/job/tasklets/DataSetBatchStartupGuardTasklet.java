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

package fr.dademo.batch.tools.batch.job.tasklets;

import fr.dademo.batch.beans.BeanValues;
import fr.dademo.batch.services.DataSetService;
import fr.dademo.batch.services.dto.DataSetDto;
import fr.dademo.data.definitions.DataSetResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static fr.dademo.batch.tools.batch.job.BatchSharedValues.KEY_BATCH_EXECUTION_CONTEXT_DATASET_DTO;

@Slf4j
public class DataSetBatchStartupGuardTasklet extends BaseDataSetTasklet implements Tasklet {

    private final DataSetService dataSetService;

    public DataSetBatchStartupGuardTasklet(@Nonnull DataSetService dataSetService) {
        this.dataSetService = dataSetService;
    }

    @Override
    public RepeatStatus execute(@Nonnull StepContribution contribution,
                                @Nonnull ChunkContext chunkContext) throws Exception {

        DataSetDto dataSetDto;
        final var dataSetResource = getDataSetResourceFromContext(contribution);
        final var dataSetMatch = getDatasetMatchUsingDataSetResource(dataSetResource);

        if (dataSetMatch.isPresent()) {
            dataSetDto = dataSetMatch.get();
            log.info("Data set is already present with state {}", dataSetDto.getState());
            if (isDataSetStateReady(dataSetDto) && !isJobForced(contribution)) {
                log.info("Will stop execution as it is not necessary and not forced");
                contribution.getStepExecution().setStatus(BatchStatus.STOPPED);
                contribution.getStepExecution().setExitStatus(ExitStatus.NOOP);
            } else {
                dataSetDto.setState(DataSetDto.DataSetDtoState.RUNNING);
                dataSetService.updateDataSet(dataSetDto);
            }
        } else {
            log.info("Creating a data set");
            dataSetDto = createDataSetFromDataSetResource(dataSetResource);
        }

        contribution.getStepExecution().getExecutionContext().put(KEY_BATCH_EXECUTION_CONTEXT_DATASET_DTO, dataSetDto);


        return RepeatStatus.FINISHED;
    }

    private boolean isDataSetStateReady(DataSetDto dataSetDto) {
        final var state = dataSetDto.getState();
        return state == DataSetDto.DataSetDtoState.READY || state == DataSetDto.DataSetDtoState.RUNNING;
    }

    private DataSetDto createDataSetFromDataSetResource(DataSetResource dataSetResource) {

        return Optional.ofNullable(dataSetResource.getParentId())
            .map(streamParentId -> dataSetService.createDataSet(
                dataSetResource.getName(),
                streamParentId
            ))
            .orElseGet(() -> dataSetService.createDataSet(
                dataSetResource.getName(),
                Objects.requireNonNull(dataSetResource.getSource()),
                Objects.requireNonNull(dataSetResource.getSourceSub())
            ));
    }

    private Optional<DataSetDto> getDatasetMatchUsingDataSetResource(DataSetResource comparedDataSetResource) {

        return dataSetService
            .getDataSetByName(comparedDataSetResource.getName())
            .stream()
            .filter(dataSetDtoComparator(comparedDataSetResource))
            .findFirst();
    }

    private Predicate<DataSetDto> dataSetDtoComparator(final DataSetResource comparedDataSetResource) {

        return dataSetDto -> Stream.of(
                Objects.equals(dataSetDto.getName(), comparedDataSetResource.getName()),
                Objects.equals(dataSetDto.getParent(), comparedDataSetResource.getParentId()),
                Objects.equals(dataSetDto.getSource(), comparedDataSetResource.getSource()),
                Objects.equals(dataSetDto.getSourceSub(), comparedDataSetResource.getSourceSub())
            )
            .allMatch(Boolean.TRUE::equals);
    }

    private boolean isJobForced(StepContribution contribution) {

        return Boolean.parseBoolean(
            contribution
                .getStepExecution()
                .getJobExecution()
                .getJobParameters()
                .getString(BeanValues.JOB_PARAMETER_FORCE)
        );
    }
}
