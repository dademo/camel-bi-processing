/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company.writers;

import fr.dademo.batch.beans.mongodb.MongoTemplateFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.bi.companies.jobs.stg.company.CompanyItemWriter;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
import fr.dademo.bi.companies.shared.BaseJobWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company.JobDefinition.COMPANY_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company.JobDefinition.COMPANY_JOB_NAME;

/**
 * @author dademo
 */
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_MONGODB_TYPE
)
public class CompanyMongoDBItemWriterImpl extends BaseJobWriter implements CompanyItemWriter {

    public static final String COLLECTION_NAME = "company";

    private final MongoTemplate mongoTemplate;

    public CompanyMongoDBItemWriterImpl(MongoTemplateFactory mongoTemplateFactory,
                                        BatchConfiguration batchConfiguration) {

        this.mongoTemplate = mongoTemplateFactory.getTemplateForConnection(
            getJobOutputDataSourceName(COMPANY_CONFIG_JOB_NAME, batchConfiguration)
                .orElseThrow(MissingJobDataSourceConfigurationException.forJob(COMPANY_JOB_NAME))
        );
    }

    @SneakyThrows
    @Override
    public void write(Chunk<? extends Company> items) {

        log.info("Writing {} company documents", items.size());
        final var result = mongoTemplate.getCollection(COLLECTION_NAME)
            .withDocumentClass(Company.class)
            .insertMany(items.getItems());
        log.info("{} items added", result.getInsertedIds().size());
    }
}
