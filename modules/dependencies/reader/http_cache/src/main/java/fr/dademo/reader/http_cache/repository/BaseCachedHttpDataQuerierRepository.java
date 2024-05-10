/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http_cache.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.reader.http.repository.DefaultHttpDataQuerierRepository;
import fr.dademo.reader.http.repository.QueryCustomizer;
import fr.dademo.reader.http.repository.handlers.QueryResponseHandler;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.util.Collections;
import java.util.List;

/**
 * @author dademo
 */
public abstract class BaseCachedHttpDataQuerierRepository extends DefaultHttpDataQuerierRepository implements CachedHttpDataQuerierRepository {

    protected BaseCachedHttpDataQuerierRepository(@Nonnull HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public InputStream basicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                                  @Nonnull List<QueryCustomizer> queryCustomizers,
                                  @Nullable QueryResponseHandler queryResponseHandler,
                                  @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators) throws IOException, InterruptedException {

        return basicQuery(
            httpInputStreamIdentifier,
            queryCustomizers,
            queryResponseHandler,
            httpStreamValidators,
            Collections.emptyList()
        );
    }
}
