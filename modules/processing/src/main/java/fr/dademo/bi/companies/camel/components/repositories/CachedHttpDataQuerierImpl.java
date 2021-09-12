package fr.dademo.bi.companies.camel.components.repositories;

import fr.dademo.bi.companies.camel.components.repositories.entities.HashDefinition;
import fr.dademo.bi.companies.camel.components.repositories.entities.HttpHashDefinition;
import fr.dademo.bi.companies.camel.components.repositories.exceptions.FailedQueryException;
import fr.dademo.bi.companies.camel.components.repositories.exceptions.HttpQueryException;
import fr.dademo.bi.companies.camel.components.repositories.exceptions.MissingResultBodyException;
import fr.dademo.bi.companies.camel.components.repositories.exceptions.UnexpectedRedirectResponseException;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.SystemUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static fr.dademo.bi.companies.tools.hash.HashTools.computeHash;
import static fr.dademo.bi.companies.tools.hash.HashTools.getHashComputerForAlgorithm;

public class CachedHttpDataQuerierImpl implements HttpDataQuerier {

    private static final Path CACHE_DIRECTORY_ROOT = Path.of(SystemUtils.getUserHome().getAbsolutePath(), ".cache", "camel-http");

    private final OkHttpClient okHttpClient;
    private final CacheHandler cacheHandler;

    public CachedHttpDataQuerierImpl(OkHttpClient okHttpClient) {
        this(okHttpClient, true);
    }

    public CachedHttpDataQuerierImpl(@Nonnull OkHttpClient okHttpClient, boolean useLocalCache) {
        this(okHttpClient, useLocalCache ? defaultCacheHandler() : null);
    }

    public CachedHttpDataQuerierImpl(@Nonnull OkHttpClient okHttpClient, @Nullable CacheHandler cacheHandler) {
        this.okHttpClient = okHttpClient;
        this.cacheHandler = cacheHandler;
    }

    public static CacheHandler defaultCacheHandler() {
        return new CacheHandler(CACHE_DIRECTORY_ROOT);
    }

    @Override
    @SneakyThrows
    public void basicQuery(
            @Nonnull URL queryUrl,
            @Nullable Duration expiration,
            @Nonnull List<HttpHashDefinition> httpHashDefinitionList,
            @Nonnull Consumer<InputStream> resultConsumer) {

        Optional.ofNullable(cacheHandler)
                .ifPresentOrElse(cache -> {
                    if (cache.hasCachedInputStream(queryUrl.toString())) {
                        cache.readFromCachedInputStream(
                                queryUrl.toString(),
                                resultConsumer
                        );
                    } else {
                        getQuery(
                                queryUrl,
                                inputStream -> cache.consumeAndCacheInputStream(
                                        inputStream,
                                        queryUrl.toString(),
                                        expiration,
                                        httpHashDefinitionList.stream().map(this::fileHashDefinitionFor).collect(Collectors.toList()),
                                        resultConsumer
                                ));
                    }
                }, () -> getQuery(queryUrl, resultConsumer));
    }

    public byte[] basicQueryByte(
            @Nonnull URL queryUrl,
            @Nullable Duration expiration,
            @Nonnull List<HttpHashDefinition> httpHashDefinitionList) {

        var byteArrayBuilder = new ByteArrayOutputStream();


        basicQuery(queryUrl,
                expiration,
                httpHashDefinitionList,
                inputStream -> {
                    try {
                        inputStream.transferTo(byteArrayBuilder);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
        );

        return byteArrayBuilder.toByteArray();
    }

    private void getQuery(URL queryUrl, Consumer<InputStream> onSuccessfulAnswer) {

        var request = new Request.Builder()
                .url(queryUrl)
                .get()
                .build();

        try (var response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new FailedQueryException(response);
            }
            if (response.isRedirect()) {
                throw new UnexpectedRedirectResponseException();
            }

            try (var inputStream = inputStreamOfResponse(response)) {
                onSuccessfulAnswer.accept(inputStream);
            }
        } catch (IOException e) {
            throw new HttpQueryException(request, e);
        }
    }

    private InputStream inputStreamOfResponse(Response response) {

        return Optional.ofNullable(response.body())
                .map(ResponseBody::byteStream)
                .orElseThrow(MissingResultBodyException::new);
    }

    @SneakyThrows
    private HashDefinition fileHashDefinitionFor(HttpHashDefinition httpHashDefinition) {

        return HashDefinition.of(
                computeHash(
                        getHashComputerForAlgorithm(httpHashDefinition.getAlgorithm()),
                        new ByteArrayInputStream(basicQueryByte(httpHashDefinition.getResourceUrl(), null, Collections.emptyList()))),
                httpHashDefinition.getAlgorithm()
        );
    }
}
