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
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseCacheRepository<T extends InputStreamIdentifier<?>> implements CacheRepository<T> {

    @Autowired
    private LockFactory lockFactory;

    @Override
    public Lock acquireCacheLockForIdentifier(@Nonnull T inputStreamIdentifier) {
        return lockFactory.createLock(inputStreamIdentifier);
    }
}
