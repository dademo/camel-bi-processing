/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.beans;

import fr.dademo.supervision.service.beans.impl.ProblemObjectMapperCustomizer;
import fr.dademo.tools.tools.object_mapper.ObjectMapperCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@Configuration
public class ServiceBeans {

    @Bean
    public ObjectMapperCustomizer problemObjectMapperCustomizer() {
        return new ProblemObjectMapperCustomizer();
    }
}
