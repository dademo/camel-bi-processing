/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.validators;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;

/**
 * @author dademo
 */
public interface CacheValidator<T extends InputStreamIdentifier<?>> {

    /**
     * Validate a {@link CachedInputStreamIdentifier} descriptor to validate or invalidate cache.
     *
     * @param cachedInputStreamIdentifier the cache identifier to validate.
     * @return is the cached file still valid.
     */
    boolean isValid(CachedInputStreamIdentifier<T> cachedInputStreamIdentifier);
}
