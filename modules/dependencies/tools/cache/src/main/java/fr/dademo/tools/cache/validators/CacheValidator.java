package fr.dademo.tools.cache.validators;

import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.stream_definitions.InputStreamIdentifier;

public interface CacheValidator<T extends InputStreamIdentifier<?>> {

    /**
     * Validate a {@link CachedInputStreamIdentifier} descriptor to validate or invalidate cache.
     *
     * @param cachedInputStreamIdentifier the cache identifier to validate.
     * @return is the cached file still valid.
     */
    boolean isValid(CachedInputStreamIdentifier<T> cachedInputStreamIdentifier);
}
