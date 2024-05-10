/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.repository;

import com.hazelcast.core.HazelcastInstance;
import fr.dademo.data.generic.stream_definitions.Cacheable;
import fr.dademo.tools.lock.configuration.LockConfiguration;
import fr.dademo.tools.lock.repository.model.HazelcastLock;
import fr.dademo.tools.lock.repository.model.Lock;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(
    name = LockConfiguration.CONFIGURATION_PROPERTY_PREFIX + ".backend",
    havingValue = LockConfiguration.LockBackend.LOCK_BACKEND_HAZELCAST
)
@Component
public class HazelcastLockFactory implements LockFactory {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Override
    @NonNull
    public Lock createLock(@Nonnull Cacheable resource) {
        return HazelcastLock.of(hazelcastInstance, resource);
    }
}
