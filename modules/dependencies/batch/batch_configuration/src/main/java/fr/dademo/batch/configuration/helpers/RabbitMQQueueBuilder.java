/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.helpers;

import fr.dademo.batch.configuration.data_sources.RabbitMQConfiguration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.amqp.core.Queue;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RabbitMQQueueBuilder {

    public static Queue getQueueFor(RabbitMQConfiguration.RabbitMQQueueConfiguration queueConfiguration) {
        return new Queue(
            queueConfiguration.getName(),
            queueConfiguration.isDurable(),
            queueConfiguration.isExclusive(),
            queueConfiguration.isAutoDelete(),
            queueConfiguration.getArguments()
        );
    }
}
