/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.repository.model;

import fr.dademo.data.generic.stream_definitions.Cacheable;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import jakarta.annotation.Nonnull;

@Slf4j
public class RedisLock implements Lock {

    @Nonnull
    private final RedissonClient redissonClient;

    @Nonnull
    private final Cacheable lockedResource;

    private final RLock lock;

    protected RedisLock(@Nonnull RedissonClient redissonClient, @Nonnull Cacheable lockedResource) {

        this.redissonClient = redissonClient;
        this.lockedResource = lockedResource;

        // Acquiring lock
        final var uniqueIdentifier = lockedResource.getUniqueIdentifier();
        log.info("Acquiring lock {}", uniqueIdentifier);
        lock = redissonClient.getLock(uniqueIdentifier);
        log.info("Lock {} acquired", uniqueIdentifier);
    }

    public static RedisLock of(@Nonnull RedissonClient redissonClient, @Nonnull Cacheable lockedResource) {
        return new RedisLock(redissonClient, lockedResource);
    }

    @Override
    public void close() {

        final var uniqueIdentifier = lockedResource.getUniqueIdentifier();
        log.debug("Releasing lock {}", uniqueIdentifier);
        lock.unlock();
        log.debug("Lock {} released", uniqueIdentifier);
    }

    @Nonnull
    @Override
    public String getDescription() {

        return String.format(
            "%s instance for resource %s using Redis instance [Redisson client %s]",
            getClass().getName(),
            lockedResource.getUniqueIdentifier(),
            redissonClient.getId()
        );
    }
}
