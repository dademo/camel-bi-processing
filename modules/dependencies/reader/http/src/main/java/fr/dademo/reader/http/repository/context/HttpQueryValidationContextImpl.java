/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository.context;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.data.generic.stream_definitions.repository.BaseValidationContext;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import jakarta.annotation.Nonnull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author dademo
 */
public class HttpQueryValidationContextImpl<T extends HttpInputStreamIdentifier> extends BaseValidationContext<T> {

    public HttpQueryValidationContextImpl(@Nonnull InputStream inputStream,
                                          @Nonnull T inputStreamIdentifier,
                                          @Nonnull List<? extends InputStreamIdentifierValidator<T>> streamValidators
    ) throws IOException {
        super(inputStream, inputStreamIdentifier, streamValidators);
    }
}
