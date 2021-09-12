package fr.dademo.bi.companies.process.naf;

import fr.dademo.bi.companies.camel.beans.CamelLoggingCounterBean;
import fr.dademo.bi.companies.camel.components.HttpComponent;
import fr.dademo.bi.companies.process.naf.entities.NafDefinition;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RouteDefinitions extends RouteBuilder {

    private static final String JOB_NAME = "naf";
    private static final String LOGGER_NAME = "camel_jobs." + JOB_NAME;
    private static final String DATASET_URL = "https://data.iledefrance.fr/explore/dataset/nomenclature-dactivites-francaise-naf-rev-2-code-ape/download";
    private static final String DATASET_URL_QUERY_PARAMETERS = "format=json";

    @Inject
    @ConfigProperty(name = "camel.jobs.naf.enabled", defaultValue = "false")
    boolean isEnabled = false;

    @Override
    public void configure() {

        if (isEnabled) {
            fromF("%s:%s?queryParameters=%s",
                    HttpComponent.COMPONENT_NAME,
                    DATASET_URL,
                    DATASET_URL_QUERY_PARAMETERS)
                    .routeId(JOB_NAME + "_process")
                    .log(LoggingLevel.DEBUG, LOGGER_NAME, "Extracting values")
                    .split().jsonpathWriteAsString("$..fields")
                    .streaming().parallelProcessing()
                    .log(LoggingLevel.TRACE, LOGGER_NAME, "Marshalling APE dataset")
                    .unmarshal().json(NafDefinition.class)
                    .log(LoggingLevel.TRACE, LOGGER_NAME, formatLogSaveTemplate())
                    .to("jpa:" + NafDefinition.class.getName() + "?flushOnSend=false")
                    .stop();
        }
    }

    private String formatLogSaveTemplate() {

        String nextValueTemplate = String.format("${bean:%s.next('%s', %d)}", CamelLoggingCounterBean.BEAN_NAME, JOB_NAME, 1);
        return String.format("Saving values (%s)", nextValueTemplate);
    }
}
