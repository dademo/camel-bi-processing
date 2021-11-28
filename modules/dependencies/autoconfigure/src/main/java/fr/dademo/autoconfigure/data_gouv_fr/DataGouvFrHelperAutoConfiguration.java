/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.autoconfigure.data_gouv_fr;

import fr.dademo.autoconfigure.generic_definitions.GenericDefinitionsAutoConfiguration;
import fr.dademo.autoconfigure.http.HttpAutoConfiguration;
import fr.dademo.data.helpers.data_gouv_fr.DefaultDataGouvFrBeans;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(DefaultDataGouvFrBeans.class)
@AutoConfigureAfter({GenericDefinitionsAutoConfiguration.class, HttpAutoConfiguration.class})
@ComponentScan("fr.dademo.data.helpers.data_gouv_fr")
public class DataGouvFrHelperAutoConfiguration {
}
