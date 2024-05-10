/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.events;

import fr.dademo.batch.beans.amqp.AmqpFactory;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
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

    private final AmqpFactory amqpFactory;

    public ApplicationPrepareMessagingOnEvents(@Nonnull AmqpFactory amqpFactory) {
        this.amqpFactory = amqpFactory;
    }

    @EventListener(ApplicationStartedEvent.class)
    @ConditionalOnProperty(value = "amqp.createQueues", matchIfMissing = true)
    public void doCreateQueues() {

        log.info("Creating queues");
        amqpFactory.getAllQueuesBuilder().forEach(AmqpFactory.QueueBuilder::build);
        log.info("All queues created");
    }

    @EventListener(ApplicationStartedEvent.class)
    @ConditionalOnProperty(value = "amqp.createExchanges", matchIfMissing = true)
    public void doCreateExchanges() {

        log.info("Creating exchanges");
        amqpFactory.getAllExchangesBuilder().forEach(AmqpFactory.ExchangeBuilder::build);
        log.info("All exchanges created");
    }
}
