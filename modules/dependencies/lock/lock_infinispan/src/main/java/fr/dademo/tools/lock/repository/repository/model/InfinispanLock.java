/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.repository.repository.model;

import fr.dademo.data.generic.stream_definitions.Cacheable;
import fr.dademo.tools.lock.repository.model.Lock;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.lock.api.ClusteredLock;
import org.infinispan.lock.api.ClusteredLockManager;

@Slf4j
public class InfinispanLock implements Lock {

    @Nonnull
    private final ClusteredLockManager lockManager;

    @Nonnull
    private final Cacheable lockedResource;

    private final ClusteredLock lock;


    protected InfinispanLock(@Nonnull ClusteredLockManager lockManager, @Nonnull Cacheable lockedResource) {

        this.lockManager = lockManager;
        this.lockedResource = lockedResource;

        // Acquiring lock
        final var uniqueIdentifier = lockedResource.getUniqueIdentifier();
        // We first ensure the lock is created
        if (lockManager.defineLock(uniqueIdentifier)) {
            log.debug("Lock {} created", uniqueIdentifier);
        }
        // ... then we acquire lock
        log.debug("Acquiring lock {}", uniqueIdentifier);
        lock = lockManager.get(uniqueIdentifier);
        lock.lock().join();
        log.debug("Lock {} acquired", uniqueIdentifier);
    }

    public static InfinispanLock of(@Nonnull ClusteredLockManager lockManager, @Nonnull Cacheable lockedResource) {
        return new InfinispanLock(lockManager, lockedResource);
    }

    @Override
    public void close() {

        final var uniqueIdentifier = lockedResource.getUniqueIdentifier();
        log.debug("Releasing lock {}", uniqueIdentifier);
        lock.unlock().join();
        log.debug("Lock {} released", uniqueIdentifier);
    }

    @Nonnull
    @Override
    public String getDescription() {

        return String.format(
            "%s instance for resource %s using Infinispan ClusteredLockManager instance [%s]",
            getClass().getName(),
            lockedResource.getUniqueIdentifier(),
            lockManager
        );
    }
}
