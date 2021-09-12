package fr.dademo.bi.companies.camel.components.repositories.entities;

import lombok.Value;

@Value(staticConstructor = "of")
public class HashDefinition {
    String hash;
    String hashAlgorithm;
}
