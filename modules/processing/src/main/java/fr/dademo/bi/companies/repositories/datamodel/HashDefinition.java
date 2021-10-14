package fr.dademo.bi.companies.repositories.datamodel;

import lombok.Value;

@Value(staticConstructor = "of")
public class HashDefinition {
    String hash;
    String hashAlgorithm;
}
