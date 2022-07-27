/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nullable;

@Configuration
@ConfigurationProperties(prefix = LockConfiguration.CONFIGURATION_PROPERTY_PREFIX)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockConfiguration {

    public static final String CONFIGURATION_PROPERTY_PREFIX = "lock";

    @Nullable
    private LockBackend backend = LockBackend.MEMORY;

    @Nullable
    private HazelcastConfiguration hazelcast;

    @Nullable
    private InfinispanConfiguration infinispan;

    @Nullable
    private RedisConfiguration redis;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum LockBackend {
        MEMORY(LockBackend.LOCK_BACKEND_MEMORY),
        HAZELCAST(LockBackend.LOCK_BACKEND_HAZELCAST),
        INFINISPAN(LockBackend.LOCK_BACKEND_INFINISPAN),
        MEMCACHED(LockBackend.LOCK_BACKEND_MEMCACHED),
        REDIS(LockBackend.LOCK_BACKEND_REDIS);

        public static final String LOCK_BACKEND_MEMORY = "MEMORY";
        public static final String LOCK_BACKEND_HAZELCAST = "HAZELCAST";
        public static final String LOCK_BACKEND_INFINISPAN = "INFINISPAN";
        public static final String LOCK_BACKEND_MEMCACHED = "MEMCACHED";
        public static final String LOCK_BACKEND_REDIS = "REDIS";
        private final String value;
    }
}
