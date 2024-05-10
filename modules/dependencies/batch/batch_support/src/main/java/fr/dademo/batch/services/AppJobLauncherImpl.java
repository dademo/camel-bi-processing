/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.services;

import fr.dademo.batch.beans.BeanValues;
import fr.dademo.batch.helpers.JobTaskExecutorWrapper;
import fr.dademo.batch.tools.batch.job.JobProvider;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

import static fr.dademo.batch.beans.BeanValues.TASK_EXECUTOR_BEAN_NAME;

/**
 * @author dademo
 */
@Slf4j
@Service
public class AppJobLauncherImpl implements AppJobLauncher {

    private final JobLauncher jobLauncher;
    private final JobTaskExecutorWrapper taskExecutor;
    private final List<JobProvider> allBatches;

    public AppJobLauncherImpl(JobLauncher jobLauncher,
                              @Qualifier(TASK_EXECUTOR_BEAN_NAME) JobTaskExecutorWrapper taskExecutor,
                              List<JobProvider> allBatches) {
        this.jobLauncher = jobLauncher;
        this.taskExecutor = taskExecutor;
        this.allBatches = allBatches;
    }

    @Override
    public boolean run(@Nonnull List<String> onlyJobs, boolean force) {

        final var applicationJobLauncher = ApplicationJobLauncher.builder()
            .jobLauncher(jobLauncher)
            .force(force)
            .build();

        log.info("Starting all jobs");
        final var jobExecutions = allBatches.stream()
            .filter(JobProvider::isJobAvailable)
            .filter(jobProvider -> onlyJobs.isEmpty() || onlyJobs.contains(jobProvider.getJobName()))
            .map(JobProvider::getJob)
            .map(applicationJobLauncher::runJob)
            .toList();

        log.info("Waiting for jobs to end");
        taskExecutor.shutdown();
        taskExecutor.waitAll();
        log.info("Jobs finished");

        return jobExecutions.stream().allMatch(this::isExitStatusConsideredValid);
    }

    private boolean isExitStatusConsideredValid(JobExecution jobExecution) {

        return Stream.of(
                ExitStatus.COMPLETED,
                ExitStatus.STOPPED,
                ExitStatus.STOPPED
            )
            .map(jobExecution.getExitStatus()::compareTo)
            .anyMatch(Integer.valueOf(0)::equals);
    }

    @Override
    public List<String> getAllAvailableJobs() {

        return allBatches.stream()
            .filter(JobProvider::isJobAvailable)
            .map(JobProvider::getJobName)
            .toList();
    }

    @Builder
    @AllArgsConstructor
    private static class ApplicationJobLauncher {

        private final JobLauncher jobLauncher;

        private final boolean force;

        @SneakyThrows
        public JobExecution runJob(@Nonnull Job job) {
            return jobLauncher.run(job, getJobParameters());
        }

        private JobParameters getJobParameters() {

            return new JobParametersBuilder()
                .addLong(BeanValues.JOB_PARAMETER_STARTED_AT, System.currentTimeMillis())
                .addString(BeanValues.JOB_PARAMETER_FORCE, Boolean.toString(force))
                .toJobParameters();
        }
    }
}
