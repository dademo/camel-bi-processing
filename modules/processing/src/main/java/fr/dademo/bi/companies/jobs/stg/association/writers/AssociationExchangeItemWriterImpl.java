/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association.writers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.batch.beans.amqp.AmqpFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.bi.companies.jobs.stg.association.AssociationItemWriter;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import fr.dademo.bi.companies.shared.AbstractApplicationAmqpWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.association.JobDefinition.ASSOCIATION_CONFIG_JOB_NAME;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + ASSOCIATION_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_AMQP_TYPE
)
public class AssociationExchangeItemWriterImpl extends AbstractApplicationAmqpWriter implements AssociationItemWriter {

    public static final String EXCHANGE_CONFIG_NAME = "association";

    private final ObjectMapper objectMapper;

    public AssociationExchangeItemWriterImpl(AmqpFactory amqpFactory,
                                             ObjectMapper objectMapper,
                                             BatchConfiguration batchConfiguration) {

        super(amqpFactory.getAmqpTemplateForDataSource(
            getJobOutputDataSourceName(ASSOCIATION_CONFIG_JOB_NAME, batchConfiguration)
                .orElseThrow(MissingJobDataSourceConfigurationException.forJob(ASSOCIATION_CONFIG_JOB_NAME))
        ));
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public void write(List<? extends Association> items) {

        log.info("Writing {} association documents", items.size());

        items.stream()
            .map(this::toMessage)
            .forEach(this::sendMessage);

        log.info("{} items written", items.size());
    }

    @SneakyThrows
    private Message toMessage(Association association) {
        return new Message(objectMapper.writeValueAsBytes(association), getMessageProperties());
    }

    private MessageProperties getMessageProperties() {

        final var messageProperties = new MessageProperties();
        messageProperties.setAppId(DEFAULT_SPRING_APPLICATION_NAME);
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setHeader("x-source-thread", Thread.currentThread().getName());
        return messageProperties;
    }

    @Nonnull
    @Override
    protected String getExchangeName() {
        return EXCHANGE_CONFIG_NAME;
    }
}
