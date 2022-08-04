/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.repository.model;

import fr.dademo.data.generic.stream_definitions.Cacheable;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class MemoryLock implements Lock {

    @Nonnull
    private final Cacheable lockedResource;

    @Nonnull
    private final ReentrantLock lock;


    protected MemoryLock(@Nonnull Cacheable lockedResource, @Nonnull ReentrantLock lock) {

        this.lockedResource = lockedResource;
        this.lock = lock;

        // Acquiring lock
        final var uniqueIdentifier = lockedResource.getUniqueIdentifier();
        log.debug("Acquiring lock {}", uniqueIdentifier);
        lock.lock();
        log.debug("Lock {} acquired ({})", uniqueIdentifier, lock);
    }

    public static MemoryLock of(@Nonnull Cacheable lockedResource, @Nonnull ReentrantLock lock) {
        return new MemoryLock(lockedResource, lock);
    }

    @Override
    public void close() {
        log.debug("Releasing lock {}", lock);
        lock.unlock();
        log.debug("Lock {} released", lock);
    }

    @Nonnull
    @Override
    public String getDescription() {

        return String.format(
            "%s instance for resource %s using a memory lock",
            getClass().getName(),
            lockedResource.getUniqueIdentifier()
        );
    }
}
