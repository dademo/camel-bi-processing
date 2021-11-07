package fr.dademo.tools.cache.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface CacheRepository<T extends InputStreamIdentifier<?>> {

    @Nonnull
    Optional<CachedInputStreamIdentifier<T>> getCachedInputStreamIdentifierOf(InputStreamIdentifier<?> inputStreamIdentifier);

    boolean hasCachedInputStream(@Nonnull T fileIdentifier);

    @Nonnull
    InputStream readFromCachedInputStream(@Nonnull T fileIdentifier) throws IOException;

    @Nonnull
    InputStream cacheInputStream(@Nonnull InputStream inputStream,
                                 @Nonnull T httpInputStreamIdentifier) throws IOException;

    void deleteFromCache(@Nonnull T httpInputStreamIdentifier) throws IOException;
}
