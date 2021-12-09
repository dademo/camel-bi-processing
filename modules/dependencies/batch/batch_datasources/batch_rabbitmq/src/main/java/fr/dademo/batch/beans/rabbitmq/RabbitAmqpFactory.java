/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.rabbitmq;

import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.helpers.RabbitMQExchangeBuilder;
import fr.dademo.batch.configuration.helpers.RabbitMQQueueBuilder;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author dademo
 */
@Component
public class RabbitAmqpFactory {

    private final Map<String, ConnectionFactory> cachedConnectionFactory = new HashMap<>();

    @Autowired
    private BatchDataSourcesConfiguration batchDataSourcesConfiguration;

    @Nonnull
    public AmqpAdmin getAmqpAdminForDataSource(@NotEmpty String rabbitMQDataSourceName) {
        return new RabbitAdmin(getConnectionFactoryForDataSource(rabbitMQDataSourceName));
    }

    @Nonnull
    public AmqpTemplate getAmqpTemplateForDataSource(@NotEmpty String rabbitMQDataSourceName) {
        return new RabbitTemplate(getConnectionFactoryForDataSource(rabbitMQDataSourceName));
    }

    public Stream<QueueBuilder> getAllQueuesBuilder() {
        return batchDataSourcesConfiguration.getRabbitmq().keySet().stream().flatMap(this::getQueueBuilderFor);
    }

    public Stream<ExchangeBuilder> getAllExchangesBuilder() {
        return batchDataSourcesConfiguration.getRabbitmq().keySet().stream().flatMap(this::getExchangeBuilderFor);
    }

    private Stream<QueueBuilder> getQueueBuilderFor(@NotEmpty String rabbitMQDataSourceName) {

        return batchDataSourcesConfiguration.getRabbitMQClientConfigurationByName(rabbitMQDataSourceName)
            .getQueues().values().stream()
            .map(RabbitMQQueueBuilder::getQueueFor)
            .map(queue -> queueBuilder(rabbitMQDataSourceName, queue));
    }

    private Stream<ExchangeBuilder> getExchangeBuilderFor(@NotEmpty String rabbitMQDataSourceName) {

        return batchDataSourcesConfiguration.getRabbitMQClientConfigurationByName(rabbitMQDataSourceName)
            .getExchanges().values().stream()
            .map(RabbitMQExchangeBuilder::getExchangeFor)
            .map(exchange -> exchangeBuilder(rabbitMQDataSourceName, exchange));
    }

    @Nonnull
    private ConnectionFactory getConnectionFactoryForDataSource(@NotEmpty String rabbitMQDataSourceName) {

        return cachedConnectionFactory.computeIfAbsent(
            rabbitMQDataSourceName,
            this::buildConnectionFactoryForDataSource
        );
    }

    @Nonnull
    private ConnectionFactory buildConnectionFactoryForDataSource(@NotEmpty String configurationName) {

        final var configuration = batchDataSourcesConfiguration.getRabbitMQClientConfigurationByName(configurationName);
        final var connectionFactory = new CachingConnectionFactory(configuration.getConnectionString());

        if (configuration.isConfirms()) {
            connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        } else {
            connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.NONE);
        }
        return connectionFactory;
    }

    private QueueBuilder queueBuilder(@NotEmpty String rabbitMQDataSourceName, @Nonnull Queue queue) {
        return () -> getAmqpAdminForDataSource(rabbitMQDataSourceName).declareQueue(queue);
    }

    private ExchangeBuilder exchangeBuilder(@NotEmpty String rabbitMQDataSourceName, @Nonnull Exchange exchange) {
        return () -> getAmqpAdminForDataSource(rabbitMQDataSourceName).declareExchange(exchange);
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
