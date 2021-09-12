package fr.dademo.bi.companies.camel.components;

import fr.dademo.bi.companies.camel.components.repositories.CachedHttpDataQuerierImpl;
import fr.dademo.bi.companies.camel.components.repositories.HttpDataQuerier;
import fr.dademo.bi.companies.camel.components.repositories.entities.HttpHashDefinition;
import fr.dademo.bi.companies.camel.components.repositories.exceptions.HashSourceDefinitionException;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.support.DefaultConsumer;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

public class HttpConsumer extends DefaultConsumer {

    private static final Logger LOGGER = Logger.getLogger(HttpConsumer.class);
    private static final Pattern httpHashDefinitionPattern = Pattern.compile("^(?<algorithm>[A-Za-z0-9_-]+):(?<uri>.+)$");

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
        getHttpDataQuerier().basicQuery(
                resourceUrl,
                endpoint.getCacheExpiration().orElse(null),
                getHttpHashDefinition(),
                this::consumeResult
        );
        LOGGER.info("Query performed");
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

        var loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        var okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor);

        Optional.ofNullable(endpoint.getConnectTimeoutSeconds())
                .map(Duration::ofSeconds)
                .ifPresent(okHttpClient::connectTimeout);

        Optional.ofNullable(endpoint.getCallReadTimeoutSeconds())
                .map(Duration::ofSeconds)
                .ifPresent(okHttpClient::readTimeout);

        Optional.ofNullable(endpoint.getCallTimeoutSeconds())
                .map(Duration::ofSeconds)
                .ifPresent(okHttpClient::callTimeout);

        return okHttpClient.build();
    }

    private List<HttpHashDefinition> getHttpHashDefinition() {

        return endpoint.getFileHashUrls().stream()
                .map(this::toHttpHashDefinition)
                .collect(Collectors.toList());
    }

    private HttpHashDefinition toHttpHashDefinition(String hashSourceDefinition) {

        var matcher = httpHashDefinitionPattern.matcher(hashSourceDefinition);
        if (!matcher.matches()) {
            throw new HashSourceDefinitionException(hashSourceDefinition);
        } else {
            try {
                return HttpHashDefinition.of(new URL(matcher.group("uri")), matcher.group("algorithm"));
            } catch (MalformedURLException e) {
                throw new HashSourceDefinitionException(hashSourceDefinition, e);
            }
        }
    }
}
