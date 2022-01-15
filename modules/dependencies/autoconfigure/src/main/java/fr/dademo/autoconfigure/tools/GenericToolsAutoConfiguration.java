/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.autoconfigure.tools;

import fr.dademo.autoconfigure.generic_definitions.GenericDefinitionsAutoConfiguration;
import fr.dademo.tools.tools.DefaultToolBeans;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(DefaultToolBeans.class)
@AutoConfigureAfter(GenericDefinitionsAutoConfiguration.class)
@ComponentScan("fr.dademo.tools.tools")
public class GenericToolsAutoConfiguration {
}
