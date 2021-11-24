/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache;

import fr.dademo.data.generic.stream_definitions.configuration.CacheConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheConfiguration.class)
@ComponentScan(basePackageClasses = {CacheAutoConfiguration.class, CacheConfiguration.class})
public class CacheAutoConfiguration {
}
