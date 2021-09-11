package fr.dademo.bi.companies.components.camel.repositories;

import lombok.Value;

import java.net.URL;

@Value(staticConstructor = "of")
public class HttpHashDefinition {
    URL resourceUrl;
    String algorithm;
}
