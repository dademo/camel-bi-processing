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
import fr.dademo.reader.http_cache.repository.exception.UncheckedInterruptedException;
import fr.dademo.tools.cache.repository.cache_index.CacheIndexRepository;
import fr.dademo.tools.cache.repository.cache_repository.CacheRepository;
import fr.dademo.tools.cache.validators.CacheFlowIgnoreChecker;
import fr.dademo.tools.cache.validators.CacheValidator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.http.HttpClient;
import java.util.List;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@ConditionalOnBean({CacheRepository.class, CacheIndexRepository.class})
@Repository
public class CachedHttpDataQuerierRepositoryImpl extends BaseCachedHttpDataQuerierRepository implements CachedHttpDataQuerierRepository {

    private final CacheRepository<HttpInputStreamIdentifier> cacheRepository;

    private final List<? extends CacheFlowIgnoreChecker> cacheFlowIgnoreCheckerList;

    public CachedHttpDataQuerierRepositoryImpl(
        @Nonnull HttpClient httpClient,
        @Nonnull CacheRepository<HttpInputStreamIdentifier> cacheRepository,
        @Nonnull List<? extends CacheFlowIgnoreChecker> cacheFlowIgnoreCheckerList) {
        super(httpClient);
        this.cacheRepository = cacheRepository;
        this.cacheFlowIgnoreCheckerList = cacheFlowIgnoreCheckerList;
    }


    @SuppressWarnings("unchecked")
    @Override
    public InputStream basicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                                  @Nonnull List<QueryCustomizer> queryCustomizers,
                                  @Nullable QueryResponseHandler queryResponseHandler,
                                  @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators,
                                  @Nonnull List<? extends CacheValidator<HttpInputStreamIdentifier>> cacheValidators) throws IOException, InterruptedException {

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

    @SuppressWarnings("java:S2142")
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
        } catch (InterruptedException e) {
            throw new UncheckedInterruptedException(e);
        }
    }

    private boolean isFlowAllowedToBeCached(HttpInputStreamIdentifier httpInputStreamIdentifier) {

        return cacheFlowIgnoreCheckerList.stream()
            .allMatch(cacheFlowIgnoreChecker -> cacheFlowIgnoreChecker.isFlowAllowedToBeCached(httpInputStreamIdentifier));
    }
}
