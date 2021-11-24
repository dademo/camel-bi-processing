/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http;

import fr.dademo.data.generic.stream_definitions.configuration.HttpConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HttpConfiguration.class)
@ComponentScan(basePackageClasses = {HttpAutoConfiguration.class, HttpConfiguration.class})
public class HttpAutoConfiguration {
}
