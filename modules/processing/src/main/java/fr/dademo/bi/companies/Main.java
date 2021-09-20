package fr.dademo.bi.companies;

import fr.dademo.bi.companies.tools.batch.batch_steps.BatchJobProvider;
import fr.dademo.bi.companies.tools.batch.job_configuration.OrderedJobsProvider;
import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jberet.runtime.JobExecutionImpl;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@QuarkusMain
public class Main {

    public static void main(String... args) {
        Quarkus.run(App.class, args);
    }

    public static class App implements QuarkusApplication {

        private static final Logger LOGGER = Logger.getLogger(App.class);
        private static final long CHECK_SLEEP_MILLIS = 50;

        // Tasks to run
        @Inject
        QuarkusJobOperator jobOperator;

        @Inject
        OrderedJobsProvider orderedJobsProvider;

        @Override
        @SneakyThrows
        public int run(String... args) {

            orderedJobsProvider.getJobProviderss().forEach(this::runJob);

            return 0;
        }

        @SneakyThrows
        public void runJob(BatchJobProvider jobProvider) {

            var job = jobProvider.getJob();

            LOGGER.info(String.format("Running job %s", job.getId()));

            var jobId = jobOperator.start(job, new Properties());

            LOGGER.info(String.format("Job %s started with id %d", job.getId(), jobId));
            LOGGER.info("Waiting for the job to end");

            ((JobExecutionImpl) jobOperator.getJobExecution(jobId)).awaitTermination(0L, TimeUnit.SECONDS);

            var jobExecution = jobOperator.getJobExecution(jobId);
            LOGGER.info(String.format("Job %s ended with status %s and exit code %s (duration: %s)",
                    job.getId(),
                    jobExecution.getBatchStatus().toString(),
                    jobExecution.getExitStatus(),
                    DurationFormatUtils.formatDuration(jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime(), "HH:mm:ss")
            ));
        }
    }
}
