/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.data_sources;

import fr.dademo.batch.configuration.data_sources.exception.MissingExchangeConfigurationException;
import fr.dademo.batch.configuration.data_sources.exception.MissingQueueConfigurationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

/**
 * @author dademo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMQConfiguration {

    public static final String DEFAULT_SPRING_APPLICATION_NAME = "JAVA_SPRING";

    @Nonnull
    @NotEmpty
    private URI connectionString;

    private boolean confirms = false;

    @Nonnull
    @NotEmpty
    private Map<String, RabbitMQQueueConfiguration> queues;

    @Nonnull
    @NotEmpty
    private Map<String, RabbitMQExchangeConfiguration> exchanges;

    public RabbitMQQueueConfiguration getQueueConfigurationByName(@Nonnull String queueName) {
        return Optional.ofNullable(queues.get(queueName)).orElseThrow(MissingQueueConfigurationException.of(queueName));
    }

    public RabbitMQExchangeConfiguration getExchangeConfigurationByName(@Nonnull String queueName) {
        return Optional.ofNullable(exchanges.get(queueName)).orElseThrow(MissingExchangeConfigurationException.of(queueName));
    }

    @Data
    public static class RabbitMQQueueConfiguration {

        @NotBlank
        private String name;

        private boolean durable = false;

        private boolean exclusive = false;

        private boolean autoDelete = false;

        private Map<String, Object> arguments;
    }

    @Data
    public static class RabbitMQExchangeConfiguration {

        @NotBlank
        private String name;

        @NotBlank
        private String type;

        private boolean durable = false;

        private boolean autoDelete = false;

        private boolean delayed = false;

        private boolean internal = false;

        private Map<String, Object> arguments;
    }
}
