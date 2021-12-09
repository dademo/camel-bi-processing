/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.beans.mongodb.MongoTemplateFactory;
import fr.dademo.batch.beans.rabbitmq.RabbitAmqpFactory;
import org.jooq.DSLContext;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import static fr.dademo.batch.beans.BeanValues.*;

/**
 * @author dademo
 */
@Configuration
public class Beans {

    @Bean(STG_DATASOURCE_DSL_CONTEXT_BEAN_NAME)
    public DSLContext stgDslContext(DataSourcesFactory dataSourcesFactory) {
        return dataSourcesFactory.getDslContext(STG_DATASOURCE_NAME);
    }

    @Bean(STG_MONGO_TEMPLATE_CONFIG_BEAN_NAME)
    public MongoTemplate stgMongoTemplate(MongoTemplateFactory mongoTemplateFactory) {
        return mongoTemplateFactory.getTemplateForConnection(STG_DATASOURCE_NAME);
    }

    @Bean(STG_AMQP_TEMPLATE_CONFIG_BEAN_NAME)
    public AmqpTemplate stgAmqpTemplate(RabbitAmqpFactory rabbitAmqpFactory) {
        return rabbitAmqpFactory.getAmqpTemplateForDataSource(STG_DATASOURCE_NAME);
    }
}
