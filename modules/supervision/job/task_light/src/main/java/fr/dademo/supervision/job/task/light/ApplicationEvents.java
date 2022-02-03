/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.job.task.light;

import fr.dademo.supervision.job.task.light.configuration.JobOutputConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author dademo
 */
@Slf4j
@Configuration
public class ApplicationEvents {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private JobOutputConfiguration jobOutputConfiguration;

    @EventListener(ApplicationReadyEvent.class)
    public void onTaskStarted() {

        log.info("Declaring output exchange");
        final var exchange = new DirectExchange(
            jobOutputConfiguration.getExchangeName(),
            jobOutputConfiguration.isDurable(),
            jobOutputConfiguration.isAutoDelete(),
            jobOutputConfiguration.getArguments()
        );
        exchange.setDelayed(jobOutputConfiguration.isDelayed());
        exchange.setInternal(jobOutputConfiguration.isInternal());

        amqpAdmin.declareExchange(exchange);
    }
}
