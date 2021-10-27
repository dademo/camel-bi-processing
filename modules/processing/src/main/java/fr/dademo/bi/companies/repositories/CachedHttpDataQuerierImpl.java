package fr.dademo.bi.companies.repositories;

import fr.dademo.bi.companies.repositories.datamodel.HashDefinition;
import fr.dademo.bi.companies.repositories.exceptions.FailedQueryException;
import fr.dademo.bi.companies.repositories.exceptions.MissingResultBodyException;
import fr.dademo.bi.companies.repositories.exceptions.UnexpectedRedirectResponseException;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Default
@Component
public class CachedHttpDataQuerierImpl extends HttpDataQuerier {

    private static final Path CACHE_DIRECTORY_ROOT = Path.of(SystemUtils.getUserHome().getAbsolutePath(), ".cache", "quarkus-http");

    private final OkHttpClient okHttpClient;
    private final CacheHandler cacheHandler;


    public CachedHttpDataQuerierImpl(@Autowired @Nonnull OkHttpClient okHttpClient, @Autowired @Nonnull CacheHandlerProvider cacheHandlerProvider) {
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
                                  @Nonnull List<HashDefinition> httpHashDefinitionList) {

        return Optional.ofNullable(cacheHandler)
                .map(cache -> {
                    if (cache.hasCachedInputStream(queryUrl.toString())) {
                        return cache.readFromCachedInputStream(queryUrl.toString());
                    } else {
                        return cache.cacheInputStream(
                                getQuery(queryUrl),
                                queryUrl.toString(),
                                expiration,
                                httpHashDefinitionList
                        );
                    }
                }).orElseGet(() -> getQuery(queryUrl));
    }

    @SneakyThrows
    private InputStream getQuery(URL queryUrl) {

        final var request = new Request.Builder()
                .url(queryUrl)
                .get()
                .build();

        final var response = okHttpClient.newCall(request).execute();
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
}
