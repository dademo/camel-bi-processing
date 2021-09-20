package fr.dademo.bi.companies.repositories;

import fr.dademo.bi.companies.repositories.entities.HashDefinition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;

public interface CacheHandler {

    boolean hasCachedInputStream(@Nonnull String inputFileIdentifier);

    InputStream readFromCachedInputStream(@Nonnull String inputFileIdentifier);

    InputStream cacheInputStream(@Nonnull InputStream inputStream,
                                 @Nonnull String inputFileIdentifier,
                                 @Nullable Duration expiration,
                                 @Nonnull List<HashDefinition> hashProvider);
}
