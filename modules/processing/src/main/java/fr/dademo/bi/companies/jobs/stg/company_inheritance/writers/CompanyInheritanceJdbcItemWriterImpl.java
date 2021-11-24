/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance.writers;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.CompanyInheritanceItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import lombok.Getter;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.JobDefinition.COMPANY_INHERITANCE_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritanceTable.COMPANY_INHERITANCE;

/**
 * @author dademo
 */
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_INHERITANCE_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class CompanyInheritanceJdbcItemWriterImpl implements CompanyInheritanceItemWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyInheritanceJdbcItemWriterImpl.class);

    @Autowired
    @Qualifier(STG_DATASOURCE_DSL_CONTEXT_BEAN_NAME)
    @Getter
    private DSLContext dslContext;

    @Override
    public void write(List<? extends CompanyInheritance> items) {

        LOGGER.info("Writing {} company inheritance documents", items.size());

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(COMPANY_INHERITANCE,
            COMPANY_INHERITANCE.FIELD_COMPANY_PREDECESSOR_SIREN,
            COMPANY_INHERITANCE.FIELD_COMPANY_SUCCESSOR_SIREN,
            COMPANY_INHERITANCE.FIELD_COMPANY_SUCCESSION_DATE,
            COMPANY_INHERITANCE.FIELD_COMPANY_HEADQUARTER_CHANGE,
            COMPANY_INHERITANCE.FIELD_COMPANY_ECONOMICAL_CONTINUITY,
            COMPANY_INHERITANCE.FIELD_COMPANY_PROCESSING_DATE
        ).values((String) null, null, null, null, null, null));

        items.stream()
            .map(this::companyInheritanceBind)
            .forEach(consumer -> consumer.accept(batchInsertStatement));

        final var batchResult = batchInsertStatement.execute();
        if (batchResult.length > 0) {
            final int totalUpdated = Arrays.stream(batchResult).sum();
            LOGGER.info("{} rows affected", totalUpdated);
        } else {
            LOGGER.error("An error occurred while running batch");
        }
    }

    private Consumer<BatchBindStep> companyInheritanceBind(CompanyInheritance companyInheritance) {

        return items -> items.bind(
            companyInheritance.getCompanyPredecessorSiren(),
            companyInheritance.getCompanySuccessorSiren(),
            companyInheritance.getCompanySuccessionDate(),
            companyInheritance.getCompanyHeaderChanged(),
            companyInheritance.getCompanyEconomicalContinuity(),
            companyInheritance.getCompanyProcessingTimestamp()
        );
    }
}
