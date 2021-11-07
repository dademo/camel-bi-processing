package fr.dademo.reader.http.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.reader.http.repository.context.QueryValidationContextImpl;
import fr.dademo.reader.http.repository.handlers.DefaultQueryResponseHandler;
import fr.dademo.reader.http.repository.handlers.QueryResponseHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public class DefaultHttpDataQuerierRepository implements HttpDataQuerierRepository {

    @Autowired
    private OkHttpClient okHttpClient;

    @Override
    public InputStream basicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                                  @Nonnull List<QueryCustomizer> queryCustomizers,
                                  @Nullable QueryResponseHandler queryResponseHandler,
                                  @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators) throws IOException {

        return performBasicQuery(httpInputStreamIdentifier,
                queryCustomizers,
                queryResponseHandler,
                httpStreamValidators
        );
    }

    @Override
    public InputStream getInputStream(@Nonnull HttpInputStreamIdentifier inputStreamIdentifier,
                                      @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> streamValidators) throws IOException {

        return basicQuery(
                inputStreamIdentifier,
                Collections.emptyList(),
                null,
                streamValidators
        );
    }

    protected InputStream performBasicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                                            @Nonnull List<QueryCustomizer> queryCustomizers,
                                            @Nullable QueryResponseHandler queryResponseHandler,
                                            @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators) throws IOException {

        var request = new Request.Builder()
                .url(httpInputStreamIdentifier.getUrl())
                .method(httpInputStreamIdentifier.getMethod(),
                        httpInputStreamIdentifier.getRequestBody()
                );

        for (final var queryCustomizer : queryCustomizers) {
            request = queryCustomizer.customizeRequest(request);
        }

        final var response = okHttpClient.newCall(request.build()).execute();

        final var inputStream = Optional.ofNullable(queryResponseHandler)
                .orElseGet(DefaultQueryResponseHandler::new)
                .handleResponse(response, this);

        if (!httpStreamValidators.isEmpty()) {
            return new QueryValidationContextImpl<>(
                    inputStream,
                    httpInputStreamIdentifier,
                    httpStreamValidators
            );
        } else {
            return inputStream;
        }
    }
}
