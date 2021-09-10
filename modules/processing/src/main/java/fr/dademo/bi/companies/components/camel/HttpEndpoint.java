package fr.dademo.bi.companies.components.camel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;
import org.apache.camel.support.DefaultEndpoint;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@UriEndpoint(firstVersion = "1.0", scheme = "httpGet", title = "HttpGet", syntax = "httpGet:url", consumerOnly = true)
public class HttpEndpoint extends DefaultEndpoint implements HttpEndpointConfiguration {

    @UriPath(description = "Path of resources")
    @Metadata(required = true)
    private String url;

    @UriParam(description = "Additional query parameters", multiValue = true)
    private String queryParameters;

    @UriParam(description = "Connection timeout in seconds", defaultValue = "5")
    private Long connectTimeoutSecond = 5L;

    @UriParam(description = "Call timeout in seconds", defaultValue = "15")
    private Long callTimeoutSecond = 15L;

    @UriParam(description = "To use local cache", defaultValue = "true")
    private Boolean useLocalCache = true;


    public HttpEndpoint(String endpointUri, String url, HttpComponent component) {
        super(endpointUri, component);
        this.url = url;
    }

    @Override
    public Producer createProducer() {
        throw new UnsupportedOperationException("Cannot push data");
    }

    @Override
    public Consumer createConsumer(Processor processor) {
        return new HttpConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
