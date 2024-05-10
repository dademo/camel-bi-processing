/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.lock.repository.LockFactory;
import fr.dademo.tools.lock.repository.model.Lock;
import jakarta.annotation.Nonnull;

public abstract class BaseCacheRepository<T extends InputStreamIdentifier<?>> implements CacheRepository<T> {

    private final LockFactory lockFactory;

    protected BaseCacheRepository(@Nonnull LockFactory lockFactory) {
        this.lockFactory = lockFactory;
    }

    @Override
    public Lock acquireCacheLockForIdentifier(@Nonnull T inputStreamIdentifier) {
        return lockFactory.createLock(inputStreamIdentifier);
    }
}
