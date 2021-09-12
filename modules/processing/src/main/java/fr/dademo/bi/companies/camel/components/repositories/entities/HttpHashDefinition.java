package fr.dademo.bi.companies.camel.components.repositories.entities;

import lombok.Value;

import java.net.URL;

@Value(staticConstructor = "of")
public class HttpHashDefinition {
    URL resourceUrl;
    String algorithm;
}
