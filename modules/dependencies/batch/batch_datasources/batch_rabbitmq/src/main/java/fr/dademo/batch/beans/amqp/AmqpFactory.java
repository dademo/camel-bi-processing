/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.amqp;

import fr.dademo.batch.beans.amqp.exception.AmqpUnknownBackendException;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.helpers.AmqpExchangeBuilder;
import fr.dademo.batch.configuration.helpers.AmqpQueueBuilder;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author dademo
 */
@Component
public class AmqpFactory {

    // Handled AMQP backends
    public static final String CONFIG_AMQP_BACKEND_RABBITMQ = "rabbitmq";


    private final Map<String, AmqpAdmin> cachedAmqpAdmin = new HashMap<>();
    private final Map<String, AmqpTemplate> cachedAmqpTemplate = new HashMap<>();

    private final BatchDataSourcesConfiguration batchDataSourcesConfiguration;

    public AmqpFactory(BatchDataSourcesConfiguration batchDataSourcesConfiguration) {
        this.batchDataSourcesConfiguration = batchDataSourcesConfiguration;
    }

    @Nonnull
    public AmqpAdmin getAmqpAdminForDataSource(@NotEmpty String dataSourceName) {

        return cachedAmqpAdmin.computeIfAbsent(
            dataSourceName,
            this::createAmqpAdminForDataSource
        );
    }

    @Nonnull
    public AmqpTemplate getAmqpTemplateForDataSource(@NotEmpty String dataSourceName) {

        return cachedAmqpTemplate.computeIfAbsent(
            dataSourceName,
            this::createAmqpTemplateForDataSource
        );
    }

    private AmqpAdmin createAmqpAdminForDataSource(String dataSourceName) {

        final var amqpConfiguration = batchDataSourcesConfiguration.getAmqpConfigurationByName(dataSourceName);

        if (CONFIG_AMQP_BACKEND_RABBITMQ.equals(amqpConfiguration.getBackend())) {
            return new RabbitAdmin(rabbitMQConnectionFactoryForDataSource(dataSourceName));
        }
        throw new AmqpUnknownBackendException(amqpConfiguration.getBackend());
    }

    private RabbitTemplate createAmqpTemplateForDataSource(String dataSourceName) {

        final var amqpConfiguration = batchDataSourcesConfiguration.getAmqpConfigurationByName(dataSourceName);

        if (CONFIG_AMQP_BACKEND_RABBITMQ.equals(amqpConfiguration.getBackend())) {
            return new RabbitTemplate(rabbitMQConnectionFactoryForDataSource(dataSourceName));
        }

        throw new AmqpUnknownBackendException(amqpConfiguration.getBackend());
    }

    public Stream<QueueBuilder> getAllQueuesBuilder() {

        return batchDataSourcesConfiguration.getAmqp()
            .keySet()
            .stream()
            .filter(v -> batchDataSourcesConfiguration.getAmqp().get(v).isEnabled())
            .flatMap(this::getQueueBuilderFor);
    }

    public Stream<ExchangeBuilder> getAllExchangesBuilder() {

        return batchDataSourcesConfiguration.getAmqp()
            .keySet()
            .stream()
            .filter(v -> batchDataSourcesConfiguration.getAmqp().get(v).isEnabled())
            .flatMap(this::getExchangeBuilderFor);
    }

    private Stream<QueueBuilder> getQueueBuilderFor(@NotEmpty String dataSourceName) {

        return batchDataSourcesConfiguration.getAmqpConfigurationByName(dataSourceName)
            .getQueues().values().stream()
            .map(AmqpQueueBuilder::getQueueFor)
            .map(queue -> queueBuilder(dataSourceName, queue));
    }

    private Stream<ExchangeBuilder> getExchangeBuilderFor(@NotEmpty String dataSourceName) {

        return batchDataSourcesConfiguration.getAmqpConfigurationByName(dataSourceName)
            .getExchanges().values().stream()
            .map(AmqpExchangeBuilder::getExchangeFor)
            .map(exchange -> exchangeBuilder(dataSourceName, exchange));
    }

    @Nonnull
    private ConnectionFactory rabbitMQConnectionFactoryForDataSource(@NotEmpty String configurationName) {

        final var configuration = batchDataSourcesConfiguration.getAmqpConfigurationByName(configurationName);
        final var connectionFactory = new CachingConnectionFactory(configuration.getConnectionString());

        if (configuration.isConfirms()) {
            connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        } else {
            connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.NONE);
        }
        return connectionFactory;
    }

    private QueueBuilder queueBuilder(@NotEmpty String dataSourceName, @Nonnull Queue queue) {
        return () -> getAmqpAdminForDataSource(dataSourceName).declareQueue(queue);
    }

    private ExchangeBuilder exchangeBuilder(@NotEmpty String dataSourceName, @Nonnull Exchange exchange) {
        return () -> getAmqpAdminForDataSource(dataSourceName).declareExchange(exchange);
    }

    @FunctionalInterface
    public interface QueueBuilder {
        void build();
    }

    @FunctionalInterface
    public interface ExchangeBuilder {
        void build();
    }
}
