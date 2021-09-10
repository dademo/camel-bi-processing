package fr.dademo.bi.companies.process.naf;

import fr.dademo.bi.companies.components.camel.HttpComponent;
import fr.dademo.bi.companies.process.naf.entities.NafDefinition;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RouteDefinitions extends RouteBuilder {

    private static final String GROUV_FR_OPENDATA_APE_DATASET_URL = "https://data.iledefrance.fr/explore/dataset/nomenclature-dactivites-francaise-naf-rev-2-code-ape/download";
    private static final String GROUV_FR_OPENDATA_APE_DATASET_URL_QUERY_PARAMETERS = "format=json";

    @Inject
    CamelContext camelContext;

    @Override
    public void configure() {

        // https://developers.redhat.com/articles/2021/05/17/integrating-systems-apache-camel-and-quarkus-red-hat-openshift
        camelContext.addComponent(HttpComponent.COMPONENT_NAME, new HttpComponent(camelContext));

        fromF("%s:%s?queryParameters=%s",
                HttpComponent.COMPONENT_NAME,
                GROUV_FR_OPENDATA_APE_DATASET_URL,
                GROUV_FR_OPENDATA_APE_DATASET_URL_QUERY_PARAMETERS)
                .routeId("ApeProcess")
                .log(LoggingLevel.INFO, "Extracting values")
                .split().jsonpathWriteAsString("$..fields")
                .streaming().parallelProcessing()
                .log(LoggingLevel.DEBUG, "Marshalling APE dataset")
                .unmarshal().json(NafDefinition.class)
                .log(LoggingLevel.DEBUG, "Saving values")
                .to("jpa:" + NafDefinition.class.getName())
                .stop();
    }
}
