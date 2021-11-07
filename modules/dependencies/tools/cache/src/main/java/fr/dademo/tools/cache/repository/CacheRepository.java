package fr.dademo.tools.cache.repository;

import fr.dademo.tools.stream_definitions.InputStreamIdentifier;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;

public interface CacheRepository<T extends InputStreamIdentifier<?>> {

    boolean hasCachedInputStream(@Nonnull T fileIdentifier);

    InputStream readFromCachedInputStream(@Nonnull T fileIdentifier) throws IOException;

    InputStream cacheInputStream(@Nonnull InputStream inputStream,
                                 @Nonnull T httpInputStreamIdentifier) throws IOException;

    void deleteFromCache(@Nonnull T httpInputStreamIdentifier) throws IOException;
}
