package fr.dademo.bi.companies.repositories.entities;

import lombok.SneakyThrows;
import lombok.Value;

import java.io.InputStream;
import java.net.URL;

@Value(staticConstructor = "of")
public class HttpHashDefinition {
    URL resourceUrl;
    String algorithm;

    // By default, we only return the answer body
    @SneakyThrows
    public String getHashFromAnswer(InputStream inputStream) {
        return new String(inputStream.readAllBytes());
    }
}
