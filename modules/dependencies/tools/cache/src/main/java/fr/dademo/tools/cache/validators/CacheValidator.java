package fr.dademo.tools.cache.validators;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;

public interface CacheValidator<T extends InputStreamIdentifier<?>> {

    /**
     * Validate a {@link CachedInputStreamIdentifier} descriptor to validate or invalidate cache.
     *
     * @param cachedInputStreamIdentifier the cache identifier to validate.
     * @return is the cached file still valid.
     */
    boolean isValid(CachedInputStreamIdentifier<T> cachedInputStreamIdentifier);
}
