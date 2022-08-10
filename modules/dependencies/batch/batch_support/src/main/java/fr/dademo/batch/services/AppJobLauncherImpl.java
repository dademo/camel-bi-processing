/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.services;

import fr.dademo.batch.helpers.JobTaskExecutorWrapper;
import fr.dademo.batch.tools.batch.job.BatchJobProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static fr.dademo.batch.beans.BeanValues.TASK_EXECUTOR_BEAN_NAME;

/**
 * @author dademo
 */
@Slf4j
@Service
public class AppJobLauncherImpl implements AppJobLauncher {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(TASK_EXECUTOR_BEAN_NAME)
    private JobTaskExecutorWrapper taskExecutor;

    @Autowired
    private List<BatchJobProvider> allBatchs;

    @Override
    public boolean runAll() {

        log.info("Starting all jobs");
        final var jobExecutions = allBatchs.stream()
            .map(BatchJobProvider::getJob)
            .filter(Objects::nonNull)
            .map(this::run)
            .collect(Collectors.toList());

        log.info("Waiting for jobs to end");
        taskExecutor.shutdown();
        taskExecutor.waitAll();
        log.info("Jobs finished");

        return jobExecutions.stream()
            .allMatch(jobExecution -> jobExecution.getExitStatus().compareTo(ExitStatus.COMPLETED) == 0);
    }

    @SneakyThrows
    private JobExecution run(@Nonnull Job job) {
        return jobLauncher.run(job, getJobParameters());
    }

    private JobParameters getJobParameters() {
        return new JobParametersBuilder()
            .addLong("startedAt", System.currentTimeMillis())
            .toJobParameters();
    }
}
