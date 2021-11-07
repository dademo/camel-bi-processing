package fr.dademo.tools.http.repository;

import fr.dademo.tools.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.tools.http.repository.handlers.QueryResponseHandler;
import fr.dademo.tools.stream_definitions.InputStreamIdentifierValidator;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public interface HttpDataQuerier {

    InputStream basicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                           @Nonnull List<QueryCustomizer> queryCustomizers,
                           @Nullable QueryResponseHandler queryResponseHandler,
                           @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators);


    default InputStream basicQuery(@Nonnull HttpInputStreamIdentifier fileIdentifier) {
        return basicQuery(fileIdentifier, Collections.emptyList(), null, Collections.emptyList());
    }

    @SneakyThrows
    default byte[] basicQueryByte(
            @Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
            @Nonnull List<QueryCustomizer> queryCustomizers,
            @Nullable QueryResponseHandler queryResponseHandler,
            @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators) {

        final var byteArrayBuilder = new ByteArrayOutputStream();

        basicQuery(httpInputStreamIdentifier,
                queryCustomizers,
                queryResponseHandler,
                httpStreamValidators
        ).transferTo(byteArrayBuilder);

        return byteArrayBuilder.toByteArray();
    }
}
