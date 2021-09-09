package fr.dademo.bi.companies.components.camel;

public interface HttpEndpointConfiguration {

    String getUrl();

    Long getConnectTimeoutSecond();

    Long getCallTimeoutSecond();

    String getQueryParameters();
}
