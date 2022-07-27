/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.repository.beans;

import fr.dademo.tools.lock.configuration.LockConfiguration;
import lombok.SneakyThrows;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.configuration.parsing.ConfigurationBuilderHolder;
import org.infinispan.lock.EmbeddedClusteredLockManagerFactory;
import org.infinispan.lock.api.ClusteredLockManager;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.util.Optional;

@ConditionalOnProperty(
    name = LockConfiguration.CONFIGURATION_PROPERTY_PREFIX + ".backend",
    havingValue = LockConfiguration.LockBackend.LOCK_BACKEND_INFINISPAN
)
@Configuration
public class InfinispanBeans {

    public static final String DEFAULT_CLUSTER_NAME = "dev";
    public static final String DEFAULT_CACHE_NAME = "dev";
    public static final String DEFAULT_JGROUPS = "default-jgroups-tcp.xml";

    @Bean
    @ConditionalOnMissingBean(GlobalConfigurationBuilder.class)
    public GlobalConfigurationBuilder infinispanGlobalConfigurationBuilder() {

        return new GlobalConfigurationBuilder()
            .clusteredDefault();
    }

    @SneakyThrows
    @Bean
    @ConditionalOnMissingBean(ConfigurationBuilderHolder.class)
    public ConfigurationBuilderHolder infinispanCacheManagerConfiguration(LockConfiguration lockConfiguration, GlobalConfiguration infinispanGlobalConfiguration) {

        final var clusterName = Optional.ofNullable(lockConfiguration.getInfinispan().getClusterName()).orElse(DEFAULT_CLUSTER_NAME);
        final var cacheName = Optional.ofNullable(lockConfiguration.getInfinispan().getCacheName()).orElse(DEFAULT_CACHE_NAME);
        final var configurationBuilderHolder = new ConfigurationBuilderHolder();

        configurationBuilderHolder.getGlobalConfigurationBuilder().clusteredDefault();
        configurationBuilderHolder.getGlobalConfigurationBuilder().transport()
            .clusterName(clusterName)
            .initialClusterSize(1)
            .nodeName(Optional.ofNullable(lockConfiguration.getInfinispan().getNodeName()).orElse(InetAddress.getLocalHost().getHostName()))
            .addProperty("configurationFile", Optional.ofNullable(lockConfiguration.getInfinispan().getClusterName()).orElse(DEFAULT_JGROUPS));
        configurationBuilderHolder.getGlobalConfigurationBuilder().defaultCacheName(cacheName);
        configurationBuilderHolder.newConfigurationBuilder(cacheName).clustering().cacheMode(CacheMode.DIST_SYNC);

        return configurationBuilderHolder;
    }

    @Bean
    @ConditionalOnMissingBean(EmbeddedCacheManager.class)
    public EmbeddedCacheManager cacheManager(ConfigurationBuilderHolder infinispanCacheManagerConfiguration) {

        final var cacheManager = new DefaultCacheManager(infinispanCacheManagerConfiguration, false);
        cacheManager.start();
        return cacheManager;
    }

    @Bean
    @ConditionalOnMissingBean(ClusteredLockManager.class)
    public ClusteredLockManager clusteredLockManager(DefaultCacheManager cacheManager) {
        return EmbeddedClusteredLockManagerFactory.from(cacheManager);
    }
}
