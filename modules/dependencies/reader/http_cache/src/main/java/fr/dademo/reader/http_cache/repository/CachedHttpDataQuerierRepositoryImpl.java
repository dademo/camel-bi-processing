/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http_cache.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.reader.http.repository.QueryCustomizer;
import fr.dademo.reader.http.repository.handlers.QueryResponseHandler;
import fr.dademo.tools.cache.repository.cache_index.CacheIndexRepository;
import fr.dademo.tools.cache.repository.cache_repository.CacheRepository;
import fr.dademo.tools.cache.validators.CacheFlowIgnoreChecker;
import fr.dademo.tools.cache.validators.CacheValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

/**
 * @author dademo
 */
@ConditionalOnBean({CacheRepository.class, CacheIndexRepository.class})
@Repository
public class CachedHttpDataQuerierRepositoryImpl extends BaseCachedHttpDataQuerierRepository implements CachedHttpDataQuerierRepository {

    @Autowired
    private CacheRepository<HttpInputStreamIdentifier> cacheRepository;

    @Autowired
    private CacheIndexRepository<HttpInputStreamIdentifier> cacheIndexRepository;

    @Autowired
    private List<CacheFlowIgnoreChecker<?>> cacheFlowIgnoreCheckerList;

    @SuppressWarnings("unchecked")
    @Override
    public InputStream basicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                                  @Nonnull List<QueryCustomizer> queryCustomizers,
                                  @Nullable QueryResponseHandler queryResponseHandler,
                                  @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators,
                                  @Nonnull List<? extends CacheValidator<HttpInputStreamIdentifier>> cacheValidators) throws IOException {

        if (!isFlowAllowedToBeCached(httpInputStreamIdentifier)) {
            return performBasicQuery(
                httpInputStreamIdentifier, queryCustomizers,
                queryResponseHandler, httpStreamValidators
            );
        } else {
            try {
                return cacheRepository.readFromInputStream(
                    httpInputStreamIdentifier,
                    () -> performUnsafeBasicQuery(
                        httpInputStreamIdentifier, queryCustomizers,
                        queryResponseHandler, httpStreamValidators
                    ),
                    cacheValidators.toArray(CacheValidator[]::new)
                );
            } catch (UncheckedIOException ex) {
                throw ex.getCause();
            }
        }
    }

    private InputStream performUnsafeBasicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                                                @Nonnull List<QueryCustomizer> queryCustomizers,
                                                @Nullable QueryResponseHandler queryResponseHandler,
                                                @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators) throws UncheckedIOException {

        try {
            return performBasicQuery(
                httpInputStreamIdentifier, queryCustomizers,
                queryResponseHandler, httpStreamValidators
            );
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private boolean isFlowAllowedToBeCached(HttpInputStreamIdentifier httpInputStreamIdentifier) {

        return cacheFlowIgnoreCheckerList.stream()
            .allMatch(cacheFlowIgnoreChecker -> cacheFlowIgnoreChecker.isFlowAllowedToBeCached(httpInputStreamIdentifier));
    }
}
