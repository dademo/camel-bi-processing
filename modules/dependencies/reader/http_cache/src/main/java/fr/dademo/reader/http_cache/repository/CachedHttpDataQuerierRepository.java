package fr.dademo.reader.http_cache.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.reader.http.repository.HttpDataQuerierRepository;
import fr.dademo.reader.http.repository.QueryCustomizer;
import fr.dademo.reader.http.repository.handlers.QueryResponseHandler;
import fr.dademo.tools.cache.validators.CacheValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CachedHttpDataQuerierRepository extends HttpDataQuerierRepository {

    InputStream basicQuery(@Nonnull HttpInputStreamIdentifier httpInputStreamIdentifier,
                           @Nonnull List<QueryCustomizer> queryCustomizers,
                           @Nullable QueryResponseHandler queryResponseHandler,
                           @Nonnull List<? extends InputStreamIdentifierValidator<HttpInputStreamIdentifier>> httpStreamValidators,
                           @Nonnull List<? extends CacheValidator<HttpInputStreamIdentifier>> cacheValidators) throws IOException;
}
