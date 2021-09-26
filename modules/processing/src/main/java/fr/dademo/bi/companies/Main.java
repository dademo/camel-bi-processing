package fr.dademo.bi.companies;

import fr.dademo.bi.companies.tools.batch.job.BatchJobProvider;
import fr.dademo.bi.companies.tools.batch.job_configuration.OrderedJobsProvider;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.job.JobExecutor;
import org.jeasy.batch.core.job.JobMetrics;
import org.jeasy.batch.core.job.JobReport;

import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Future;
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
        JobExecutor jobExecutor;

        @Inject
        OrderedJobsProvider orderedJobsProvider;


        @Override
        @SneakyThrows
        public int run(String... args) {

            try {
                orderedJobsProvider.getJobProviders().stream()
                        .map(this::runJob)
                        .map(this::waitForJobToComplete)
                        .forEach(this::handleJobResult);
            } finally {
                jobExecutor.awaitTermination(0L, TimeUnit.SECONDS);
                jobExecutor.shutdown();
            }

            return 0;
        }

        @SneakyThrows
        private Future<JobReport> runJob(BatchJobProvider jobProvider) {

            var job = jobProvider.getJob();

            LOGGER.info(String.format("Running job %s", job.getName()));
            return jobExecutor.submit(job);
        }

        @SneakyThrows
        private JobReport waitForJobToComplete(Future<JobReport> jobReportFuture) {
            LOGGER.info("Waiting for job to end");
            return jobReportFuture.get();
        }

        private void handleJobResult(JobReport jobReport) {

            switch (jobReport.getStatus()) {
                case COMPLETED:
                    LOGGER.info(String.format("Job '%s' completed (%s)%n" +
                                    "%s",
                            jobReport.getJobName(),
                            jobReport.getStatus().name(),
                            formatJobMetrics(jobReport.getMetrics())
                    ));
                    persistJobResult(jobReport);
                    break;
                case FAILED:
                case ABORTED:
                    LOGGER.warn(String.format("Job '%s' stopped (%s)%n" +
                                    "%s%n" +
                                    "Exception: %s",
                            jobReport.getJobName(),
                            jobReport.getStatus().name(),
                            formatJobMetrics(jobReport.getMetrics()),
                            jobReport.getLastError().getLocalizedMessage()
                    ));
                    persistJobResult(jobReport);
                    break;
                default:
                    LOGGER.warn(String.format("Unhandled job state (%s)", jobReport.getStatus().name()));
            }
        }

        private String formatJobMetrics(JobMetrics jobMetrics) {

            return String.format(
                    "Started on %s, ended on %s (duration: %s)%n" +
                            "Read: %d%n" +
                            "Write: %d%n" +
                            "Errors: %d",
                    jobMetrics.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    jobMetrics.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    jobMetrics.getDuration().toString(),
                    jobMetrics.getReadCount(),
                    jobMetrics.getWriteCount(),
                    jobMetrics.getErrorCount()
            );
        }

        private void persistJobResult(JobReport jobReport) {
            // TODO
        }
    }
}
