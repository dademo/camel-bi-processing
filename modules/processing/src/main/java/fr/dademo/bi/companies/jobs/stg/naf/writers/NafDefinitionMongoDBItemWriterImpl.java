/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf.writers;

import fr.dademo.batch.beans.mongodb.MongoTemplateFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.bi.companies.jobs.stg.naf.NafDefinitionItemWriter;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.shared.AbstractMongoDBWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company.JobDefinition.COMPANY_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company.JobDefinition.COMPANY_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.naf.JobDefinition.NAF_CONFIG_JOB_NAME;

/**
 * @author dademo
 */
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + NAF_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_MONGODB_TYPE
)
public class NafDefinitionMongoDBItemWriterImpl extends AbstractMongoDBWriter implements NafDefinitionItemWriter {

    public static final String COLLECTION_NAME = "naf";

    private final MongoTemplate mongoTemplate;

    public NafDefinitionMongoDBItemWriterImpl(MongoTemplateFactory mongoTemplateFactory,
                                              BatchConfiguration batchConfiguration) {

        this.mongoTemplate = mongoTemplateFactory.getTemplateForConnection(
            getJobOutputDataSourceName(COMPANY_CONFIG_JOB_NAME, batchConfiguration)
                .orElseThrow(MissingJobDataSourceConfigurationException.forJob(COMPANY_JOB_NAME))
        );
    }

    @SneakyThrows
    @Override
    public void write(List<? extends NafDefinition> items) {

        log.info("Writing {} naf definition documents", items.size());
        final var result = mongoTemplate.getCollection(COLLECTION_NAME)
            .withDocumentClass(NafDefinition.class)
            .insertMany(items);
        log.info("{} items added", result.getInsertedIds().size());
    }
}
