package fr.dademo.bi.companies.repositories;

import fr.dademo.bi.companies.repositories.datamodel.HashDefinition;
import fr.dademo.bi.companies.repositories.exception.FailedQueryException;
import fr.dademo.bi.companies.repositories.exception.MissingResultBodyException;
import fr.dademo.bi.companies.repositories.exception.UnexpectedRedirectResponseException;
import fr.dademo.bi.companies.repositories.file.identifier.FileIdentifier;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class CachedHttpDataQuerierImpl extends HttpDataQuerier {

    private final OkHttpClient okHttpClient;
    private final CacheHandler cacheHandler;

    public CachedHttpDataQuerierImpl(@Nonnull OkHttpClient okHttpClient, @Nonnull CacheHandlerProvider cacheHandlerProvider) {
        this(okHttpClient, cacheHandlerProvider.getCacheHandler());
    }

    public CachedHttpDataQuerierImpl(@Nonnull OkHttpClient okHttpClient, @Nullable CacheHandler cacheHandler) {
        this.okHttpClient = okHttpClient;
        this.cacheHandler = cacheHandler;
    }

    @Override
    public InputStream basicQuery(@Nonnull FileIdentifier<?> fileIdentifier,
                                  @Nonnull List<HashDefinition> httpHashDefinitionList) {

        return Optional.ofNullable(cacheHandler)
                .map(cache -> {
                    if (cache.hasCachedInputStream(fileIdentifier)) {
                        return cache.readFromCachedInputStream(fileIdentifier);
                    } else {
                        return cache.cacheInputStream(
                                getQuery(fileIdentifier.getBaseUrl()),
                                fileIdentifier,
                                httpHashDefinitionList
                        );
                    }
                }).orElseGet(() -> getQuery(fileIdentifier.getBaseUrl()));
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
