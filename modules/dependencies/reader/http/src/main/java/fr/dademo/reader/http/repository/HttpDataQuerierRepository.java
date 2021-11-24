/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.data.generic.stream_definitions.repository.DataStreamGetter;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.reader.http.repository.handlers.QueryResponseHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author dademo
 */
public interface HttpDataQuerierRepository extends DataStreamGetter<HttpInputStreamIdentifier> {

    InputStream basicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                           @Nonnull List<QueryCustomizer> queryCustomizers,
                           @Nullable QueryResponseHandler queryResponseHandler,
                           @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators) throws IOException;


    default InputStream basicQuery(@Nonnull HttpInputStreamIdentifier fileIdentifier) throws IOException {
        return basicQuery(fileIdentifier, Collections.emptyList(), null, Collections.emptyList());
    }

    default byte[] basicQueryByte(
        @Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
        @Nonnull List<QueryCustomizer> queryCustomizers,
        @Nullable QueryResponseHandler queryResponseHandler,
        @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators) throws IOException {

        final var byteArrayBuilder = new ByteArrayOutputStream();

        basicQuery(httpInputStreamIdentifier,
            queryCustomizers,
            queryResponseHandler,
            httpStreamValidators
        ).transferTo(byteArrayBuilder);

        return byteArrayBuilder.toByteArray();
    }
}
