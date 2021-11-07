package fr.dademo.bi.companies.repositories.datamodel;

import fr.dademo.bi.companies.repositories.file.identifier.FileIdentifier;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.InputStream;

@Value(staticConstructor = "of")
public class HttpHashDefinition {
    FileIdentifier<?> fileIdentifier;
    String algorithm;

    // By default, we only return the answer body
    @SneakyThrows
    public String getHashFromAnswer(InputStream inputStream) {
        return new String(inputStream.readAllBytes());
    }
}
