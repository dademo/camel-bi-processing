package fr.dademo.bi.companies.repositories;

import fr.dademo.bi.companies.repositories.entities.HashDefinition;
import fr.dademo.bi.companies.repositories.entities.HttpHashDefinition;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public abstract class HttpDataQuerier {

    public abstract InputStream basicQuery(@Nonnull URL queryUrl,
                                           @Nullable Duration expiration,
                                           @Nonnull List<HashDefinition> httpHashDefinitionList);


    public InputStream basicQuery(@Nonnull URL queryUrl) {
        return basicQuery(queryUrl, null, Collections.emptyList());
    }

    public InputStream basicQuery(@Nonnull URL queryUrl,
                                  @Nonnull List<HashDefinition> hashDefinitionList) {
        return basicQuery(queryUrl, null, hashDefinitionList);
    }

    public void basicQuery(@Nonnull URL queryUrl,
                           @Nonnull Consumer<InputStream> resultConsumer) {
        basicQuery(queryUrl, null, Collections.emptyList(), resultConsumer);
    }

    public void basicQuery(@Nonnull URL queryUrl,
                           @Nullable Duration expiration,
                           @Nonnull List<HashDefinition> hashDefinitionList,
                           @Nonnull Consumer<InputStream> resultConsumer) {

        resultConsumer.accept(basicQuery(
                queryUrl,
                expiration,
                hashDefinitionList
        ));
    }

    @SneakyThrows
    public byte[] basicQueryByte(
            @Nonnull URL queryUrl,
            @Nullable Duration expiration,
            @Nonnull List<HashDefinition> hashDefinitionList) {

        var byteArrayBuilder = new ByteArrayOutputStream();

        basicQuery(queryUrl,
                expiration,
                hashDefinitionList
        ).transferTo(byteArrayBuilder);

        return byteArrayBuilder.toByteArray();
    }

    @SneakyThrows
    public HashDefinition fileHashDefinitionFor(HttpHashDefinition httpHashDefinition) {

        return HashDefinition.of(
                httpHashDefinition.getHashFromAnswer(
                        basicQuery(
                                httpHashDefinition.getResourceUrl(),
                                null,
                                Collections.emptyList()
                        )),
                httpHashDefinition.getAlgorithm()
        );
    }
}
