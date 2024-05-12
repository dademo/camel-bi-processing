/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.repository.repository;

import fr.dademo.data.generic.stream_definitions.Cacheable;
import fr.dademo.tools.lock.configuration.LockConfiguration;
import fr.dademo.tools.lock.repository.LockFactory;
import fr.dademo.tools.lock.repository.model.Lock;
import fr.dademo.tools.lock.repository.repository.model.InfinispanLock;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.infinispan.lock.api.ClusteredLockManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@ConditionalOnProperty(
    name = LockConfiguration.CONFIGURATION_PROPERTY_PREFIX + ".backend",
    havingValue = LockConfiguration.LockBackend.LOCK_BACKEND_INFINISPAN
)
@Component
public class InfinispanLockFactory implements LockFactory {

    private final ClusteredLockManager lockManager;

    public InfinispanLockFactory(@Nonnull ClusteredLockManager lockManager) {
        this.lockManager = lockManager;
    }

    @Override
    @NonNull
    public Lock createLock(@Nonnull Cacheable resource) {
        return InfinispanLock.of(lockManager, resource);
    }
}
