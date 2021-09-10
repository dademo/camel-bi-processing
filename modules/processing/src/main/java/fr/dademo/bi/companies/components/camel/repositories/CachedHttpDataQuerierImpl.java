package fr.dademo.bi.companies.components.camel.repositories;

import fr.dademo.bi.companies.components.camel.repositories.exceptions.FailedQueryException;
import fr.dademo.bi.companies.components.camel.repositories.exceptions.HttpQueryException;
import fr.dademo.bi.companies.components.camel.repositories.exceptions.MissingResultBodyException;
import fr.dademo.bi.companies.components.camel.repositories.exceptions.UnexpectedRedirectResponseException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

import static fr.dademo.bi.companies.components.camel.repositories.CacheTools.*;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CachedHttpDataQuerierImpl implements HttpDataQuerier {

    private static final Path CACHE_DIRECTORY_ROOT = Path.of(SystemUtils.getUserHome().getAbsolutePath(), ".cache", "camel-http");
    private final OkHttpClient okHttpClient;
    private final boolean useLocalCache;

    @Override
    @SneakyThrows
    public void basicQuery(
            @NonNull URL queryUrl,
            @NonNull Consumer<InputStream> resultConsumer) {

        if (useLocalCache && hasCachedInputStream(queryUrl.toString(), CACHE_DIRECTORY_ROOT)) {
            readFromCachedInputStream(queryUrl.toString(), CACHE_DIRECTORY_ROOT, resultConsumer);
        } else {
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
                    if (useLocalCache) {
                        consumeAndCacheInputStream(
                                inputStream,
                                queryUrl.toString(),
                                CACHE_DIRECTORY_ROOT,
                                resultConsumer
                        );
                    } else {
                        resultConsumer.accept(inputStream);
                    }
                }
            } catch (IOException e) {
                throw new HttpQueryException(request, e);
            }
        }
    }

    private InputStream inputStreamOfResponse(Response response) {

        return Optional.ofNullable(response.body())
                .map(ResponseBody::byteStream)
                .orElseThrow(MissingResultBodyException::new);
    }
}
