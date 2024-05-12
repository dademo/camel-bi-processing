/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.data_sources;

import fr.dademo.batch.configuration.data_sources.exception.MissingExchangeConfigurationException;
import fr.dademo.batch.configuration.data_sources.exception.MissingQueueConfigurationException;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmqpConfiguration {

    public static final String DEFAULT_SPRING_APPLICATION_NAME = "JAVA_SPRING";

    private boolean enabled = true;

    @Nonnull
    @NotEmpty
    private String backend;

    @Nonnull
    @NotEmpty
    private URI connectionString;

    private boolean confirms = false;

    @Nonnull
    @NotEmpty
    private Map<String, AmqpQueueConfiguration> queues;

    @Nonnull
    @NotEmpty
    private Map<String, AmqpExchangeConfiguration> exchanges;

    public AmqpQueueConfiguration getQueueConfigurationByName(@Nonnull String queueName) {
        return Optional.ofNullable(queues.get(queueName)).orElseThrow(MissingQueueConfigurationException.of(queueName));
    }

    public AmqpExchangeConfiguration getExchangeConfigurationByName(@Nonnull String queueName) {
        return Optional.ofNullable(exchanges.get(queueName)).orElseThrow(MissingExchangeConfigurationException.of(queueName));
    }

    @SuppressWarnings("unused")
    @Data
    public static class AmqpQueueConfiguration {

        @NotBlank
        private String name;

        private boolean durable = false;

        private boolean exclusive = false;

        private boolean autoDelete = false;

        private Map<String, Object> arguments;
    }

    @SuppressWarnings("unused")
    @Data
    public static class AmqpExchangeConfiguration {

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
