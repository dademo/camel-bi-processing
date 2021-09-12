package fr.dademo.bi.companies;

import fr.dademo.bi.companies.camel.components.HttpComponent;
import fr.dademo.bi.companies.process.stg.companies_history.CompaniesHistoryRouteDefinitions;
import fr.dademo.bi.companies.process.stg.naf.NafJobRouteDefinitions;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.SneakyThrows;
import org.apache.camel.CamelContext;
import org.apache.camel.quarkus.core.CamelRuntime;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.util.Arrays;

@QuarkusMain
public class Main {

    public static void main(String... args) {
        Quarkus.run(App.class, args);
    }

    public static class App implements QuarkusApplication {

        private static final Logger LOGGER = Logger.getLogger(App.class);

        @Inject
        CamelContext camelContext;

        @Inject
        CamelRuntime camelRuntime;

        @Inject
        @ConfigProperty(name = "camel.spool-directory", defaultValue = "${java.io.tmpdir}/camel/camel-tmp-#uuid#")
        String spoolDirectory = "${java.io.tmpdir}/camel/camel-tmp-#uuid#";

        // Tasks to run
        @Inject
        NafJobRouteDefinitions nafJobRouteDefinitions;

        @Inject
        CompaniesHistoryRouteDefinitions companiesHistoryRouteDefinitions;

        @Override
        public int run(String... args) {

            LOGGER.info("Bootstraping application");
            // https://developers.redhat.com/articles/2021/05/17/integrating-systems-apache-camel-and-quarkus-red-hat-openshift
            camelContext.addComponent(HttpComponent.COMPONENT_NAME, new HttpComponent(camelContext));

            camelContext.getStreamCachingStrategy().setSpoolDirectory(spoolDirectory);

            camelContext.setAutoStartup(false);

            camelRuntime.start(args);
            LOGGER.info("Application is ready");

            LOGGER.info("Starting all jobs for jobs to end");
            Arrays.asList(
                    nafJobRouteDefinitions,
                    companiesHistoryRouteDefinitions
            ).forEach(this::startIfEnabled);
            LOGGER.info("Waiting for jobs to end");

            return camelRuntime.waitForExit();
        }

        @SneakyThrows
        private void startIfEnabled(AppJobRouteBuilder appJobRouteBuilder) {

            if (appJobRouteBuilder.isEnabled()) {
                LOGGER.info(String.format("Starting route %s", appJobRouteBuilder.getRouteId()));
                camelContext.getRouteController().startRoute(appJobRouteBuilder.getRouteId());
            }
        }
    }
}
