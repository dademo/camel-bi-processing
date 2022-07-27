/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.beans;

import fr.dademo.tools.lock.configuration.LockConfiguration;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Optional;

@ConditionalOnProperty(
    name = LockConfiguration.CONFIGURATION_PROPERTY_PREFIX + ".backend",
    havingValue = LockConfiguration.LockBackend.LOCK_BACKEND_REDIS
)
@Configuration
public class RedisBeans {

    @Bean
    @ConditionalOnMissingBean(Config.class)
    public Config redissonConfiguration(LockConfiguration lockConfiguration) {

        final var redissonConfiguration = new Config();
        switch (lockConfiguration.getRedis().getClusterMembers().size()) {
            case 0:
                throw new BeanCreationException("No address provided for Redis configuration");
            case 1:
                final var singleServerConfiguration = redissonConfiguration.useSingleServer();
                singleServerConfiguration.setAddress(lockConfiguration.getRedis().getClusterMembers().get(0));

                Optional.ofNullable(lockConfiguration.getRedis().getUserName()).ifPresent(singleServerConfiguration::setUsername);
                Optional.ofNullable(lockConfiguration.getRedis().getPassword()).ifPresent(singleServerConfiguration::setPassword);

                break;
            default:
                final var clusterServerConfiguration = redissonConfiguration.useClusterServers();
                clusterServerConfiguration.setNodeAddresses(lockConfiguration.getRedis().getClusterMembers());

                Optional.ofNullable(lockConfiguration.getRedis().getUserName()).ifPresent(clusterServerConfiguration::setUsername);
                Optional.ofNullable(lockConfiguration.getRedis().getPassword()).ifPresent(clusterServerConfiguration::setPassword);

                break;
        }

        return redissonConfiguration;
    }

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(Config redissonConfiguration) {
        return Redisson.create(redissonConfiguration);
    }

    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public RedisConnectionFactory redisConnectionFactory(Config redissonConfiguration) {
        return new RedissonConnectionFactory(redissonConfiguration);
    }
}
