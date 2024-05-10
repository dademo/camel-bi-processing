/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_index;

import fr.dademo.data.generic.stream_definitions.Cacheable;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.lock.repository.LockFactory;
import fr.dademo.tools.lock.repository.model.Lock;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

public abstract class BaseCacheIndexRepository<T extends InputStreamIdentifier<?>> implements CacheIndexRepository<T> {

    private final LockFactory lockFactory;

    protected BaseCacheIndexRepository(@Nonnull LockFactory lockFactory) {
        this.lockFactory = lockFactory;
    }

    @Override
    public Lock acquireLock() {
        return lockFactory.createLock(IndexLock.of(this));
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    private static final class IndexLock implements Cacheable {

        private static final long serialVersionUID = -6523259374758503225L;

        private final transient BaseCacheIndexRepository<?> parentRef;

        @Nonnull
        @Override
        public String getUniqueIdentifier() {
            return parentRef.toString() + "index.lock";
        }
    }
}
