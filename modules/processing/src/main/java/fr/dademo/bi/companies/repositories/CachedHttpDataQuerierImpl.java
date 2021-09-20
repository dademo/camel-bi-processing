package fr.dademo.bi.companies.repositories;

import fr.dademo.bi.companies.repositories.entities.HashDefinition;
import fr.dademo.bi.companies.repositories.entities.HttpHashDefinition;
import fr.dademo.bi.companies.repositories.exceptions.FailedQueryException;
import fr.dademo.bi.companies.repositories.exceptions.MissingResultBodyException;
import fr.dademo.bi.companies.repositories.exceptions.UnexpectedRedirectResponseException;
import io.quarkus.arc.DefaultBean;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.SystemUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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

@DefaultBean
@ApplicationScoped
public class CachedHttpDataQuerierImpl implements HttpDataQuerier {

    private static final Path CACHE_DIRECTORY_ROOT = Path.of(SystemUtils.getUserHome().getAbsolutePath(), ".cache", "quarkus-http");

    private final OkHttpClient okHttpClient;
    private final CacheHandler cacheHandler;

    @Inject
    public CachedHttpDataQuerierImpl(@Nonnull OkHttpClient okHttpClient, @Nonnull CacheHandlerProvider cacheHandlerProvider) {
        this(okHttpClient, cacheHandlerProvider.getCacheHandler());
    }

    public CachedHttpDataQuerierImpl(@Nonnull OkHttpClient okHttpClient) {
        this(okHttpClient, true);
    }

    public CachedHttpDataQuerierImpl(@Nonnull OkHttpClient okHttpClient, boolean useLocalCache) {
        this(okHttpClient, () -> useLocalCache ? defaultCacheHandler() : null);
    }

    public CachedHttpDataQuerierImpl(@Nonnull OkHttpClient okHttpClient, @Nullable CacheHandler cacheHandler) {
        this.okHttpClient = okHttpClient;
        this.cacheHandler = cacheHandler;
    }

    public static CacheHandler defaultCacheHandler() {
        return new CacheHandlerImpl(CACHE_DIRECTORY_ROOT);
    }

    @Override
    public InputStream basicQuery(@Nonnull URL queryUrl,
                                  @Nullable Duration expiration,
                                  @Nonnull List<HttpHashDefinition> httpHashDefinitionList) {

        return Optional.ofNullable(cacheHandler)
                .map(cache -> {
                    if (cache.hasCachedInputStream(queryUrl.toString())) {
                        return cache.readFromCachedInputStream(queryUrl.toString());
                    } else {
                        return cache.cacheInputStream(
                                getQuery(queryUrl),
                                queryUrl.toString(),
                                expiration,
                                httpHashDefinitionList.stream()
                                        .map(this::fileHashDefinitionFor)
                                        .collect(Collectors.toList()
                                        )
                        );
                    }
                }).orElseGet(() -> getQuery(queryUrl));
    }

    @Override
    @SneakyThrows
    public void basicQuery(
            @Nonnull URL queryUrl,
            @Nullable Duration expiration,
            @Nonnull List<HttpHashDefinition> httpHashDefinitionList,
            @Nonnull Consumer<InputStream> resultConsumer) {

        resultConsumer.accept(basicQuery(
                queryUrl,
                expiration,
                httpHashDefinitionList
        ));
    }

    @SneakyThrows
    public byte[] basicQueryByte(
            @Nonnull URL queryUrl,
            @Nullable Duration expiration,
            @Nonnull List<HttpHashDefinition> httpHashDefinitionList) {

        var byteArrayBuilder = new ByteArrayOutputStream();

        basicQuery(queryUrl,
                expiration,
                httpHashDefinitionList
        ).transferTo(byteArrayBuilder);

        return byteArrayBuilder.toByteArray();
    }

    @SneakyThrows
    private InputStream getQuery(URL queryUrl) {

        var request = new Request.Builder()
                .url(queryUrl)
                .get()
                .build();

        var response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new FailedQueryException(response);
        }
        if (response.isRedirect()) {
            throw new UnexpectedRedirectResponseException();
        }

        return inputStreamOfResponse(response);
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
