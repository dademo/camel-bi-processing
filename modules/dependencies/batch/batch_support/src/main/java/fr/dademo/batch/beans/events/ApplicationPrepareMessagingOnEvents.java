/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.events;

import fr.dademo.batch.beans.rabbitmq.RabbitAmqpFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author dademo
 */
@Slf4j
@Configuration
public class ApplicationPrepareMessagingOnEvents {

    @Autowired
    RabbitAmqpFactory rabbitAmqpFactory;

    @EventListener(ApplicationStartedEvent.class)
    @ConditionalOnProperty(value = "amqp.createQueues", matchIfMissing = true)
    public void doCreateQueues() {

        log.info("Creating queues");
        rabbitAmqpFactory.getAllQueuesBuilder().forEach(RabbitAmqpFactory.QueueBuilder::build);
        log.info("All queues created");
    }

    @EventListener(ApplicationStartedEvent.class)
    @ConditionalOnProperty(value = "amqp.createExchanges", matchIfMissing = true)
    public void doCreateExchanges() {

        log.info("Creating exchanges");
        rabbitAmqpFactory.getAllExchangesBuilder().forEach(RabbitAmqpFactory.ExchangeBuilder::build);
        log.info("All exchanges created");
    }
}
