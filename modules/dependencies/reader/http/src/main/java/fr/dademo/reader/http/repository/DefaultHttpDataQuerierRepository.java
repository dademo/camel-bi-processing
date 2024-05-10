/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.reader.http.repository.context.HttpQueryValidationContextImpl;
import fr.dademo.reader.http.repository.handlers.DefaultQueryResponseHandler;
import fr.dademo.reader.http.repository.handlers.QueryResponseHandler;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author dademo
 */
public class DefaultHttpDataQuerierRepository implements HttpDataQuerierRepository {


    private final HttpClient httpClient;

    public DefaultHttpDataQuerierRepository(@Nonnull HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public InputStream basicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                                  @Nonnull List<QueryCustomizer> queryCustomizers,
                                  @Nullable QueryResponseHandler queryResponseHandler,
                                  @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators) throws IOException, InterruptedException {

        return performBasicQuery(httpInputStreamIdentifier,
            queryCustomizers,
            queryResponseHandler,
            httpStreamValidators
        );
    }

    @Override
    public InputStream getInputStream(@Nonnull HttpInputStreamIdentifier inputStreamIdentifier,
                                      @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> streamValidators) throws IOException, InterruptedException {

        return basicQuery(
            inputStreamIdentifier,
            Collections.emptyList(),
            null,
            streamValidators
        );
    }

    @SneakyThrows(URISyntaxException.class)
    protected InputStream performBasicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                                            @Nonnull List<QueryCustomizer> queryCustomizers,
                                            @Nullable QueryResponseHandler queryResponseHandler,
                                            @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators) throws IOException, InterruptedException {


        var httpRequest = HttpRequest.newBuilder()
            .uri(httpInputStreamIdentifier.getUrl().toURI())
            .method(
                httpInputStreamIdentifier.getMethod(),
                httpInputStreamIdentifier.getBodyStream().getBodyPublisher()
            );

        httpInputStreamIdentifier.getUrl().toURI();

        for (final var queryCustomizer : queryCustomizers) {
            httpRequest = queryCustomizer.customizeRequest(httpRequest);
        }

        final var response = httpClient.send(httpRequest.build(), HttpResponse.BodyHandlers.ofInputStream());

        final var inputStream = Optional.ofNullable(queryResponseHandler)
            .orElseGet(DefaultQueryResponseHandler::new)
            .handleResponse(response, this);

        if (!httpStreamValidators.isEmpty()) {
            return new HttpQueryValidationContextImpl<>(
                inputStream,
                httpInputStreamIdentifier,
                httpStreamValidators
            );
        } else {
            return inputStream;
        }
    }
}
