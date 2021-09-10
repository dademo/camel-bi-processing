package fr.dademo.bi.companies.components.camel;

import fr.dademo.bi.companies.components.camel.repositories.CachedHttpDataQuerierImpl;
import fr.dademo.bi.companies.components.camel.repositories.HttpDataQuerier;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.support.DefaultConsumer;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Optional;
import java.util.zip.ZipEntry;

public class HttpConsumer extends DefaultConsumer {

    private static final Logger LOGGER = Logger.getLogger(HttpConsumer.class);

    private final HttpEndpoint endpoint;

    public HttpConsumer(HttpEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        LOGGER.info("Creating consumer");
        this.endpoint = endpoint;
    }

    @Override
    protected void doStart() throws Exception {
        var urlQueryParameters = Optional.ofNullable(endpoint.getQueryParameters())
                .filter(v -> !v.isBlank())
                .map("?"::concat)
                .orElse("");
        var resourceUrl = new URL(endpoint.getUrl() + urlQueryParameters);
        super.doStart();
        getResource(resourceUrl);
    }

    public void getResource(URL resourceUrl) {

        LOGGER.info("Fetching data");
        getHttpDataQuerier().basicQuery(resourceUrl, this::consumeResult);
        LOGGER.info("Query performed");
        shutdown();
    }

    @SneakyThrows
    private void consumeResult(InputStream inputStream) {
        var exchange = endpoint.createExchange();
        exchange.getIn().setBody(inputStream.readAllBytes());
        getProcessor().process(exchange);
    }

    @SneakyThrows
    private void processZipEntry(ZipEntry zipEntry, InputStream inputStream) {

        if (!zipEntry.isDirectory()) {
            LOGGER.info(String.format("Processing ZIP file %s", zipEntry.getName()));
            getProcessor().process(createExchange(zipEntry, inputStream));
        }
    }

    @SneakyThrows
    private Exchange createExchange(ZipEntry zipEntry, InputStream inputStream) {

        var exchange = endpoint.createExchange();
        exchange.getIn().setBody(inputStream.readAllBytes());

        exchange.getIn().setHeader("name", zipEntry.getName());
        exchange.getIn().setHeader("size", zipEntry.getSize());
        exchange.getIn().setHeader("time", zipEntry.getTimeLocal());

        return exchange;
    }

    private HttpDataQuerier getHttpDataQuerier() {
        return new CachedHttpDataQuerierImpl(buildOKHttpClient(), endpoint.getUseLocalCache());
    }

    private OkHttpClient buildOKHttpClient() {

        var okHttpClient = new OkHttpClient.Builder();


        Optional.ofNullable(endpoint.getConnectTimeoutSecond())
                .map(Duration::ofSeconds)
                .ifPresent(okHttpClient::connectTimeout);

        Optional.ofNullable(endpoint.getCallTimeoutSecond())
                .map(Duration::ofSeconds)
                .ifPresent(okHttpClient::callTimeout);

        return okHttpClient.build();
    }
}
