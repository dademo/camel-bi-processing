/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.cache.repository.lock.CacheLock;
import fr.dademo.tools.cache.validators.CacheValidator;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author dademo
 */
public interface CacheRepository<T extends InputStreamIdentifier<?>> {

    CacheLock acquireCacheLockForIdentifier(@Nonnull T inputStreamIdentifier);

    @Nonnull
    Optional<CachedInputStreamIdentifier<T>> getCachedInputStreamIdentifierOf(InputStreamIdentifier<?> inputStreamIdentifier);

    @Nonnull
    InputStream readFromCachedInputStream(@Nonnull CachedInputStreamIdentifier<T> cachedInputStreamIdentifier) throws IOException;

    @Nonnull
    InputStream cacheInputStream(@Nonnull InputStream inputStream,
                                 @Nonnull T inputStreamIdentifier) throws IOException;

    void deleteFromCache(@Nonnull T inputStreamIdentifier) throws IOException;

    @Nonnull
    @SuppressWarnings("unchecked")
    default InputStream readFromInputStream(@Nonnull T inputStreamIdentifier,
                                            @Nonnull Supplier<InputStream> onMissingInputStreamSupplier,
                                            @Nonnull CacheValidator<T>... cacheValidators
    ) throws IOException {

        final var cachedInputStreamIdentifier = getCachedInputStreamIdentifierOf(inputStreamIdentifier);
        if (cachedInputStreamIdentifier.isPresent()) {
            final var cacheValidatorsMatch = Stream
                .of(cacheValidators)
                .allMatch(validator -> validator.isValid(cachedInputStreamIdentifier.get()));
            if (cacheValidatorsMatch) {
                return readFromCachedInputStream(cachedInputStreamIdentifier.get());
            }
        }
        // Default behavior, we cache values
        return cacheInputStream(onMissingInputStreamSupplier.get(), inputStreamIdentifier);
    }

    default boolean hasCachedInputStream(@Nonnull T inputStreamIdentifier) {
        return getCachedInputStreamIdentifierOf(inputStreamIdentifier).isPresent();
    }
}
