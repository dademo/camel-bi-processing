/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.repository.lock.CacheLock;
import fr.dademo.tools.cache.repository.lock.CacheLockProvider;
import fr.dademo.tools.cache.repository.lock.MemoryCacheLockProvider;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class BaseCacheRepository<T extends InputStreamIdentifier<?>> implements CacheRepository<T> {

    private final Map<T, CacheLockProvider> fileCacheLockMap = new HashMap<>();

    @Override
    public CacheLock acquireCacheLockForIdentifier(@Nonnull T inputStreamIdentifier) {

        return (
            Optional.ofNullable(fileCacheLockMap.get(inputStreamIdentifier))
                .orElseGet(() -> createAndGetProviderForIdentifier(inputStreamIdentifier))
        ).acquireLock();
    }

    private CacheLockProvider createAndGetProviderForIdentifier(T fileIdentifier) {

        final var lockProvider = new MemoryCacheLockProvider();
        fileCacheLockMap.put(fileIdentifier, lockProvider);
        return lockProvider;
    }
}
