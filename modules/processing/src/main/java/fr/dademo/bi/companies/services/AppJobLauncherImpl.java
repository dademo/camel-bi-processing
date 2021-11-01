package fr.dademo.bi.companies.services;

import fr.dademo.bi.companies.tools.batch.job.BatchJobProvider;
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
