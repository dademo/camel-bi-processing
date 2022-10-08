/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.repository.model;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import fr.dademo.data.generic.stream_definitions.Cacheable;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

@Slf4j
public class HazelcastLock implements Lock {

    public static final String MAP_NAME = "dev_cache";

    @Nonnull
    private final HazelcastInstance hazelcastInstance;

    @Nonnull
    private final Cacheable lockedResource;

    protected HazelcastLock(@Nonnull HazelcastInstance hazelcastInstance, @Nonnull Cacheable lockedResource) {

        this.hazelcastInstance = hazelcastInstance;
        this.lockedResource = lockedResource;

        // Acquiring lock
        final var uniqueIdentifier = lockedResource.getUniqueIdentifier();
        log.info("Acquiring lock {}", uniqueIdentifier);
        getLockMap().lock(uniqueIdentifier);
        log.info("Lock {} acquired", uniqueIdentifier);
    }

    public static HazelcastLock of(@Nonnull HazelcastInstance hazelcastInstance, @Nonnull Cacheable lockedResource) {
        return new HazelcastLock(hazelcastInstance, lockedResource);
    }

    @SuppressWarnings("java:S2235")
    @Override
    public void close() {

        final var uniqueIdentifier = lockedResource.getUniqueIdentifier();
        log.debug("Releasing lock {}", uniqueIdentifier);
        try {
            getLockMap().unlock(uniqueIdentifier);
        } catch (IllegalMonitorStateException ex) {
            getLockMap().forceUnlock(uniqueIdentifier);
        }
        log.debug("Lock {} released", uniqueIdentifier);
    }

    @Nonnull
    @Override
    public String getDescription() {

        return String.format(
            "%s instance for resource %s using Hazelcast instance [%s]",
            getClass().getName(),
            lockedResource.getUniqueIdentifier(),
            hazelcastInstance.getName()
        );
    }

    private IMap<String, ?> getLockMap() {
        return hazelcastInstance.getMap(MAP_NAME);
    }
}
