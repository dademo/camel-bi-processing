package fr.dademo.bi.companies.repositories;

import fr.dademo.bi.companies.repositories.datamodel.HashDefinition;
import fr.dademo.bi.companies.repositories.datamodel.HttpHashDefinition;
import fr.dademo.bi.companies.repositories.file.identifier.FileIdentifier;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public abstract class HttpDataQuerier {

    public abstract InputStream basicQuery(@Nonnull FileIdentifier<?> fileIdentifier,
                                           @Nonnull List<HashDefinition> httpHashDefinitionList);


    public InputStream basicQuery(@Nonnull FileIdentifier<?> fileIdentifier) {
        return basicQuery(fileIdentifier, Collections.emptyList());
    }

    public void basicQuery(@Nonnull FileIdentifier<?> fileIdentifier,
                           @Nonnull Consumer<InputStream> resultConsumer) {
        basicQuery(fileIdentifier, Collections.emptyList(), resultConsumer);
    }

    public void basicQuery(@Nonnull FileIdentifier<?> fileIdentifier,
                           @Nonnull List<HashDefinition> hashDefinitionList,
                           @Nonnull Consumer<InputStream> resultConsumer) {

        resultConsumer.accept(basicQuery(
                fileIdentifier,
                hashDefinitionList
        ));
    }

    @SneakyThrows
    public byte[] basicQueryByte(
            @Nonnull FileIdentifier<?> fileIdentifier,
            @Nonnull List<HashDefinition> hashDefinitionList) {

        final var byteArrayBuilder = new ByteArrayOutputStream();

        basicQuery(fileIdentifier,
                hashDefinitionList
        ).transferTo(byteArrayBuilder);

        return byteArrayBuilder.toByteArray();
    }

    @SneakyThrows
    public HashDefinition fileHashDefinitionFor(HttpHashDefinition httpHashDefinition) {

        return HashDefinition.of(
                httpHashDefinition.getHashFromAnswer(
                        basicQuery(
                                httpHashDefinition.getFileIdentifier(),
                                Collections.emptyList()
                        )),
                httpHashDefinition.getAlgorithm()
        );
    }
}
