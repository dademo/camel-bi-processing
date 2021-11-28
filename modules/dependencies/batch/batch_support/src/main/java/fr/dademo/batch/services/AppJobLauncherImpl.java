/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.services;

import fr.dademo.batch.tools.batch.job.BatchJobProvider;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

/**
 * @author dademo
 */
@Service
public class AppJobLauncherImpl implements AppJobLauncher {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private List<BatchJobProvider> allBatchs;

    @Override
    public void runAll() {

        allBatchs.stream()
            .map(BatchJobProvider::getJob)
            .filter(Objects::nonNull)
            .forEach(this::run);

    }

    @SneakyThrows
    private void run(@Nonnull Job job) {
        jobLauncher.run(job, getJobParameters());
    }

    private JobParameters getJobParameters() {
        return new JobParametersBuilder()
            .addLong("startedAt", System.currentTimeMillis())
            .toJobParameters();
    }
}
