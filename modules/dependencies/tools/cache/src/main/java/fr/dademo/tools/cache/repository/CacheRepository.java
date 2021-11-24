/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author dademo
 */
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
