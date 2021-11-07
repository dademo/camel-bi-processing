package fr.dademo.reader.http_cache.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.reader.http.repository.QueryCustomizer;
import fr.dademo.reader.http.repository.handlers.QueryResponseHandler;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.cache.repository.CacheLockRepository;
import fr.dademo.tools.cache.repository.CacheRepository;
import fr.dademo.tools.cache.validators.CacheFlowIgnoreChecker;
import fr.dademo.tools.cache.validators.CacheValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ConditionalOnBean({CacheRepository.class, CacheLockRepository.class})
@Repository
public class CachedHttpDataQuerierRepositoryImpl extends BaseCachedHttpDataQuerierRepository implements CachedHttpDataQuerierRepository {

    @Autowired
    private CacheRepository<HttpInputStreamIdentifier> cacheRepository;

    @Autowired
    private CacheLockRepository<HttpInputStreamIdentifier> cacheLockRepository;

    @Autowired
    private List<CacheFlowIgnoreChecker<InputStreamIdentifier<?>>> cacheFlowIgnoreCheckerList;

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
            if (isInputStreamCached(httpInputStreamIdentifier, cacheValidators)) {
                return cacheRepository.readFromCachedInputStream(httpInputStreamIdentifier);
            } else {
                return cacheRepository.cacheInputStream(
                        performBasicQuery(
                                httpInputStreamIdentifier, queryCustomizers,
                                queryResponseHandler, httpStreamValidators
                        ),
                        httpInputStreamIdentifier
                );
            }
        }
    }

    private boolean isFlowAllowedToBeCached(HttpInputStreamIdentifier httpInputStreamIdentifier) {

        return cacheFlowIgnoreCheckerList.stream()
                .allMatch(cacheFlowIgnoreChecker -> cacheFlowIgnoreChecker.isFlowAllowedToBeCached(httpInputStreamIdentifier));
    }

    private boolean isInputStreamCached(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                                        @Nonnull List<? extends CacheValidator<HttpInputStreamIdentifier>> cacheValidators) {

        return cacheRepository.getCachedInputStreamIdentifierOf(httpInputStreamIdentifier)
                .map(cachedInputStreamIdentifier -> allValidatorsValidateCachedInputStream(cachedInputStreamIdentifier, cacheValidators))
                .orElse(false);
    }

    private boolean allValidatorsValidateCachedInputStream(CachedInputStreamIdentifier<HttpInputStreamIdentifier> cachedInputStreamIdentifier,
                                                           List<? extends CacheValidator<HttpInputStreamIdentifier>> cacheValidators) {
        return cacheValidators.stream().allMatch(validator -> validator.isValid(cachedInputStreamIdentifier));
    }
}
