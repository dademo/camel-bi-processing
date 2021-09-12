package fr.dademo.bi.companies.process.companies_history;

import fr.dademo.bi.companies.camel.beans.CamelLoggingCounterBean;
import fr.dademo.bi.companies.camel.components.HttpComponent;
import fr.dademo.bi.companies.process.companies_history.entities.CompanyHistory;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.zipfile.ZipFileDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class RouteDefinitions extends RouteBuilder {

    private static final String JOB_NAME = "companies_history";
    private static final String LOGGER_NAME = "camel_jobs." + JOB_NAME;
    private static final String DATASET_URL = "https://files.data.gouv.fr/insee-sirene/StockEtablissementHistorique_utf8.zip";
    private static final String DATASET_URL_QUERY_PARAMETERS = "";

    @Inject
    @ConfigProperty(name = "camel.jobs.companies-history.enabled", defaultValue = "false")
    boolean isEnabled = false;

    @Inject
    @ConfigProperty(name = "camel.jobs.companies-history.batch-size", defaultValue = "10000")
    int batchSize = 10000;

    @Override
    public void configure() {

        if (isEnabled) {
            var zipFileDataFormat = new ZipFileDataFormat();
            zipFileDataFormat.setUsingIterator(false);      // We only have a single file
            zipFileDataFormat.setMaxDecompressedSize(-1);   // File will exceed default 1Go size

            fromF("%s:%s?queryParameters=%s",
                    HttpComponent.COMPONENT_NAME,
                    DATASET_URL,
                    DATASET_URL_QUERY_PARAMETERS)
                    .routeId(JOB_NAME + "_process")
                    .log(LoggingLevel.DEBUG, LOGGER_NAME, "Unzipping")
                    .unmarshal(zipFileDataFormat).streamCaching()
                    .log(LoggingLevel.DEBUG, LOGGER_NAME, "Splitting body")
                    .split(body().tokenize("\n", batchSize, true)).streaming()
                    .log(LoggingLevel.INFO, LOGGER_NAME, "Extracting values")
                    .unmarshal().bindy(BindyType.Csv, CompanyHistory.class)
                    .log(LoggingLevel.INFO, LOGGER_NAME, formatLogSaveTemplate())
                    .to("jpa:" + List.class.getName() + "?flushOnSend=false")
                    .stop();
        }
    }

    private String formatLogSaveTemplate() {

        String currentValueTemplate = String.format("${bean:%s.get('%s')}", CamelLoggingCounterBean.BEAN_NAME, JOB_NAME);
        String nextValueTemplate = String.format("${bean:%s.next('%s', %d)}", CamelLoggingCounterBean.BEAN_NAME, JOB_NAME, batchSize);
        return String.format("Saving values (%s -> %s)", currentValueTemplate, nextValueTemplate);
    }
}
