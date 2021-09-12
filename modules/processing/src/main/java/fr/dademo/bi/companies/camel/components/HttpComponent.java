package fr.dademo.bi.companies.camel.components;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.support.DefaultComponent;

import java.util.Map;

public class HttpComponent extends DefaultComponent {

    public static final String COMPONENT_NAME = "httpGet";

    public HttpComponent() {
    }

    public HttpComponent(CamelContext context) {
        super(context);
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) {
        return new HttpEndpoint(uri, remaining, this);
    }
}
