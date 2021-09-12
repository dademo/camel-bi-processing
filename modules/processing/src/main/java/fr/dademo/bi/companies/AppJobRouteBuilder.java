package fr.dademo.bi.companies;

import org.apache.camel.builder.RouteBuilder;

public abstract class AppJobRouteBuilder extends RouteBuilder {

    public abstract boolean isEnabled();

    public abstract String getRouteId();
}
