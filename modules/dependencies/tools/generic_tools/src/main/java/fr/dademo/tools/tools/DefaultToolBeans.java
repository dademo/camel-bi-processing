/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.tools.tools.object_mapper.ObjectMapperCustomizer;
import fr.dademo.tools.tools.object_mapper.ObjectMapperDateCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Configuration
public class DefaultToolBeans {

    public static final List<ObjectMapperCustomizer> DEFAULT_OBJECT_MAPPER_CUSTOMIZERS = List.of(
        new ObjectMapperDateCustomizer()
    );

    @Bean
    public ObjectMapperCustomizer objectMapperDateCustomizerBean() {
        return new ObjectMapperDateCustomizer();
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper defaultObjectMapper(List<ObjectMapperCustomizer> objectMapperCustomizers) {

        final var mapper = new ObjectMapper();

        objectMapperCustomizers.forEach(objectMapperCustomizer -> objectMapperCustomizer.customize(mapper));

        return mapper;
    }
}
