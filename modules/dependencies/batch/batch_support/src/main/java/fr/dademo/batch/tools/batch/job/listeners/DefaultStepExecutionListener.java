/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import java.time.Duration;

@Slf4j
public class DefaultStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.debug("Running step {}", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        log.debug("Execution of step {} ended with code {} ({})\n{}",
            stepExecution.getStepName(),
            stepExecution.getExitStatus().getExitCode(),
            Duration.between(
                stepExecution.getStartTime().toInstant(),
                stepExecution.getEndTime().toInstant()
            ).toString(),
            stepExecution.getSummary()
        );
        return null;
    }
}
