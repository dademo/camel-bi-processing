/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.beans;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import fr.dademo.tools.lock.configuration.LockConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@ConditionalOnProperty(
    name = LockConfiguration.CONFIGURATION_PROPERTY_PREFIX + ".backend",
    havingValue = LockConfiguration.LockBackend.LOCK_BACKEND_HAZELCAST
)
@Configuration
public class HazelcastBeans {

    public static final String DEFAULT_CLUSTER_NAME = "dev-cluster";

    @Bean
    @ConditionalOnMissingBean(HazelcastInstance.class)
    public HazelcastInstance hazelcastInstance(LockConfiguration lockConfiguration) {

        if (lockConfiguration.getHazelcast().isLocalInstance()) {
            return getLocalHazelcastInstance(lockConfiguration);
        } else {
            return getRemoteHazelcastInstance(lockConfiguration);
        }
    }

    private HazelcastInstance getLocalHazelcastInstance(LockConfiguration lockConfiguration) {

        final var config = new Config();
        final var networkConfig = config.getNetworkConfig();
        final var restConfig = networkConfig.getRestApiConfig();
        final var multicastConfig = networkConfig.getJoin().getMulticastConfig();

        config.setClusterName(
            Optional.ofNullable(lockConfiguration.getHazelcast().getClusterName())
                .orElse(DEFAULT_CLUSTER_NAME)
        );
        Optional.ofNullable(lockConfiguration.getHazelcast().getCpMembersCount()).ifPresent(config.getCPSubsystemConfig()::setCPMemberCount);
        config.getIntegrityCheckerConfig().setEnabled(true);

        restConfig.setEnabled(lockConfiguration.getHazelcast().isRestEnabled());
        multicastConfig.setEnabled(lockConfiguration.getHazelcast().isMulticastEnabled());

        return Hazelcast.newHazelcastInstance(config);
    }

    private HazelcastInstance getRemoteHazelcastInstance(LockConfiguration lockConfiguration) {

        final var clientConfig = new ClientConfig();
        clientConfig.setClusterName(
            Optional.ofNullable(lockConfiguration.getHazelcast().getClusterName())
                .orElse(DEFAULT_CLUSTER_NAME)
        );
        lockConfiguration.getHazelcast().getPeers().forEach(clientConfig.getNetworkConfig()::addAddress);

        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}
