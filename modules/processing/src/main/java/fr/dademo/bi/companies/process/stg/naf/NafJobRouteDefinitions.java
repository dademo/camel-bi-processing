package fr.dademo.bi.companies.process.stg.naf;

import fr.dademo.bi.companies.AppJobRouteBuilder;
import fr.dademo.bi.companies.camel.beans.CamelLoggingCounterBean;
import fr.dademo.bi.companies.camel.components.HttpComponent;
import fr.dademo.bi.companies.process.stg.naf.entities.NafDefinition;
import lombok.Getter;
import org.apache.camel.LoggingLevel;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.exception.ConstraintViolationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class NafJobRouteDefinitions extends AppJobRouteBuilder {

    public static final String JOB_NAME = "naf";
    public static final String ROUTE_ID = JOB_NAME + "_process";
    public static final String PERSISTENCE_UNIT_NAME = "stg";
    private static final String LOGGER_NAME = "camel_jobs." + JOB_NAME;
    private static final String DATASET_URL = "https://data.iledefrance.fr/explore/dataset/nomenclature-dactivites-francaise-naf-rev-2-code-ape/download";
    private static final String DATASET_URL_QUERY_PARAMETERS = "format=json";

    @Getter
    @Inject
    @ConfigProperty(name = "camel.jobs.naf.enabled", defaultValue = "false")
    boolean isEnabled = false;

    public String getRouteId() {
        return ROUTE_ID;
    }

    @Override
    public void configure() {

        fromF("%s:%s?queryParameters=%s",
                HttpComponent.COMPONENT_NAME,
                DATASET_URL,
                DATASET_URL_QUERY_PARAMETERS
        )
                .routeId(ROUTE_ID)
                .autoStartup(false)
                // There is some duplicates we will ignore
                .onException(ConstraintViolationException.class).log(LoggingLevel.WARN, LOGGER_NAME, "Will ignore duplicated NAF code '${body.nafCode}'").continued(true).end()
                // Processing
                .log(LoggingLevel.DEBUG, LOGGER_NAME, "Extracting values")
                .split().jsonpathWriteAsString("$..fields")
                .streaming().parallelProcessing()
                .log(LoggingLevel.TRACE, LOGGER_NAME, "Marshalling APE dataset")
                .unmarshal().json(NafDefinition.class)
                .log(LoggingLevel.TRACE, LOGGER_NAME, formatLogSaveTemplate())
                .toF("jpa:%s" +
                                "?flushOnSend=true" +
                                "&usePersist=true" +
                                "&persistenceUnit=%s",
                        NafDefinition.class.getName(),
                        PERSISTENCE_UNIT_NAME)
                .stop();
    }

    private String formatLogSaveTemplate() {

        String nextValueTemplate = String.format("${bean:%s.next('%s', %d)}", CamelLoggingCounterBean.BEAN_NAME, JOB_NAME, 1);
        return String.format("Saving values (%s)", nextValueTemplate);
    }
}
