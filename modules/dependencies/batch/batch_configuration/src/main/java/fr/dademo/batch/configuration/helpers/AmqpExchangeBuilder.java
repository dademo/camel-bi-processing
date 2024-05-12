/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.helpers;

import fr.dademo.batch.configuration.data_sources.AmqpConfiguration;
import fr.dademo.batch.configuration.helpers.exception.UnsupportedExchangeTypeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.amqp.core.*;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AmqpExchangeBuilder {

    public static Exchange getExchangeFor(AmqpConfiguration.AmqpExchangeConfiguration exchangeConfiguration) {

        var exchange = switch (exchangeConfiguration.getType().toLowerCase()) {
            case "direct" -> new DirectExchange(
                exchangeConfiguration.getName(),
                exchangeConfiguration.isDurable(),
                exchangeConfiguration.isAutoDelete(),
                exchangeConfiguration.getArguments()
            );
            case "fanout" -> new FanoutExchange(
                exchangeConfiguration.getName(),
                exchangeConfiguration.isDurable(),
                exchangeConfiguration.isAutoDelete(),
                exchangeConfiguration.getArguments()
            );
            case "headers" -> new HeadersExchange(
                exchangeConfiguration.getName(),
                exchangeConfiguration.isDurable(),
                exchangeConfiguration.isAutoDelete(),
                exchangeConfiguration.getArguments()
            );
            case "topic" -> new TopicExchange(
                exchangeConfiguration.getName(),
                exchangeConfiguration.isDurable(),
                exchangeConfiguration.isAutoDelete(),
                exchangeConfiguration.getArguments()
            );
            default -> throw new UnsupportedExchangeTypeException(exchangeConfiguration.getType());
        };

        exchange.setDelayed(exchangeConfiguration.isDelayed());
        exchange.setInternal(exchangeConfiguration.isInternal());

        return exchange;
    }
}
