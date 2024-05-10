/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http_cache.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.reader.http.repository.HttpDataQuerierRepository;
import fr.dademo.reader.http.repository.QueryCustomizer;
import fr.dademo.reader.http.repository.handlers.QueryResponseHandler;
import fr.dademo.tools.cache.validators.CacheValidator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author dademo
 */
public interface CachedHttpDataQuerierRepository extends HttpDataQuerierRepository {

    InputStream basicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                           @Nonnull List<QueryCustomizer> queryCustomizers,
                           @Nullable QueryResponseHandler queryResponseHandler,
                           @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators,
                           @Nonnull List<? extends CacheValidator<HttpInputStreamIdentifier>> cacheValidators) throws IOException, InterruptedException;
}
