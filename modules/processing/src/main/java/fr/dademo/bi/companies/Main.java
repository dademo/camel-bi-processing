package fr.dademo.bi.companies;

import fr.dademo.bi.companies.tools.batch.job_configuration.JobProvider;
import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.SneakyThrows;
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
        JobProvider applicationJobProvider;

        @Override
        @SneakyThrows
        public int run(String... args) {

            var jobId = jobOperator.start(applicationJobProvider.getJob(), new Properties());
            ((JobExecutionImpl) jobOperator.getJobExecution(jobId)).awaitTermination(0L, TimeUnit.SECONDS);

            return 0;
        }
    }
}
