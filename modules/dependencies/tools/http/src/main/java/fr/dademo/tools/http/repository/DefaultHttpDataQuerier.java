package fr.dademo.tools.http.repository;

import fr.dademo.tools.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.tools.http.repository.context.QueryValidationContextImpl;
import fr.dademo.tools.http.repository.handlers.DefaultQueryResponseHandler;
import fr.dademo.tools.http.repository.handlers.QueryResponseHandler;
import fr.dademo.tools.stream_definitions.InputStreamIdentifierValidator;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Repository
public class DefaultHttpDataQuerier implements HttpDataQuerier {

    @Autowired
    private OkHttpClient okHttpClient;

    @Override
    @SneakyThrows
    public InputStream basicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                                  @Nonnull List<QueryCustomizer> queryCustomizers,
                                  @Nullable QueryResponseHandler queryResponseHandler,
                                  @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators) {

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

        return new QueryValidationContextImpl<>(
                inputStream,
                httpInputStreamIdentifier,
                httpStreamValidators
        );
    }
}
