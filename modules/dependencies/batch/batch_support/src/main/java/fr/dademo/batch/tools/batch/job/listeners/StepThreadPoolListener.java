/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job.listeners;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@AllArgsConstructor
public class StepThreadPoolListener implements StepExecutionListener {

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Override
    public void beforeStep(@Nonnull StepExecution stepExecution) {

        // We initialize the thread pool to start thread and linked resources
        log.info("Initializing thread pool {}", threadPoolTaskExecutor);
        threadPoolTaskExecutor.initialize();
    }

    @Override
    public ExitStatus afterStep(@Nonnull StepExecution stepExecution) {

        // We stop the Thred pool
        log.info("Stopping thread pool {}", threadPoolTaskExecutor);
        threadPoolTaskExecutor.shutdown();
        return null;
    }
}
