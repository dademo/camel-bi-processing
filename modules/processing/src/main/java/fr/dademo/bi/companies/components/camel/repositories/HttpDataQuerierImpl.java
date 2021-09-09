package fr.dademo.bi.companies.components.camel.repositories;

import fr.dademo.bi.companies.components.camel.repositories.exceptions.FailedQueryException;
import fr.dademo.bi.companies.components.camel.repositories.exceptions.HttpQueryException;
import fr.dademo.bi.companies.components.camel.repositories.exceptions.MissingResultBodyException;
import fr.dademo.bi.companies.components.camel.repositories.exceptions.UnexpectedRedirectResponseException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class HttpDataQuerierImpl implements HttpDataQuerier {

    private final OkHttpClient okHttpClient;

    @Override
    @SneakyThrows
    public void basicQuery(URL queryUrl, Consumer<InputStream> resultConsumer) {

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
                resultConsumer.accept(inputStream);
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
}
