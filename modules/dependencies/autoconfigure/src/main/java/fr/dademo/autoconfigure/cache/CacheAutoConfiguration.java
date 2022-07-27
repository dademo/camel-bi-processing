/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.autoconfigure.cache;

import fr.dademo.autoconfigure.generic_definitions.GenericDefinitionsAutoConfiguration;
import fr.dademo.tools.cache.configuration.CacheConfiguration;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(CachedInputStreamIdentifier.class)
@ConditionalOnProperty(
    value = CacheAutoConfiguration.CONFIGURATION_PREFIX + "." + CacheAutoConfiguration.CONFIGURATION_ENABLED,
    havingValue = CacheAutoConfiguration.CONFIGURATION_ENABLED_VALUE_TRUE
)
@AutoConfigureAfter(GenericDefinitionsAutoConfiguration.class)
@EnableConfigurationProperties(CacheConfiguration.class)
@ComponentScan("fr.dademo.tools.cache")
public class CacheAutoConfiguration {

    public static final String CONFIGURATION_PREFIX = "cache";
    public static final String CONFIGURATION_ENABLED = "enabled";
    public static final String CONFIGURATION_ENABLED_VALUE_TRUE = "true";
}
