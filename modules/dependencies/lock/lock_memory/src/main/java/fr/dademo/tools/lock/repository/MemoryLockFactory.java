/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.repository;

import fr.dademo.data.generic.stream_definitions.Cacheable;
import fr.dademo.tools.lock.configuration.LockConfiguration;
import fr.dademo.tools.lock.repository.model.Lock;
import fr.dademo.tools.lock.repository.model.MemoryLock;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@ConditionalOnProperty(
    name = LockConfiguration.CONFIGURATION_PROPERTY_PREFIX + ".backend",
    havingValue = LockConfiguration.LockBackend.LOCK_BACKEND_MEMORY,
    matchIfMissing = true
)
@Component
public class MemoryLockFactory implements LockFactory {

    @Nonnull
    private final Map<String, ReentrantLock> locksMap;

    public MemoryLockFactory() {
        locksMap = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    @NonNull
    public Lock createLock(@Nonnull Cacheable resource) {

        final var resourceUniqueIdentifier = resource.getUniqueIdentifier();
        return MemoryLock.of(
            resource,
            Optional.ofNullable(locksMap.putIfAbsent(resourceUniqueIdentifier, new ReentrantLock()))
                .orElseGet(() -> locksMap.get(resourceUniqueIdentifier))
        );
    }
}
