/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance.writers;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.CompanyInheritanceItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritanceRecord;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritanceTable;
import fr.dademo.bi.companies.shared.AbstractApplicationJdbcWriter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.BatchBindStep;
import org.jooq.Insert;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.JobDefinition.COMPANY_INHERITANCE_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.JobDefinition.COMPANY_INHERITANCE_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritanceTable.DEFAULT_COMPANY_INHERITANCE_TABLE;

/**
 * @author dademo
 */
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_INHERITANCE_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class CompanyInheritanceJdbcItemWriterImpl extends AbstractApplicationJdbcWriter<CompanyInheritance, CompanyInheritanceRecord> implements CompanyInheritanceItemWriter {

    private final CompanyInheritanceTable companyInheritanceTable;

    public CompanyInheritanceJdbcItemWriterImpl(
        DataSourcesFactory dataSourcesFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration
    ) {

        super(
            dataSourcesFactory.getJobOutputDslContextByDataSourceName(
                getJobOutputDataSourceName(COMPANY_INHERITANCE_CONFIG_JOB_NAME, batchConfiguration)
                    .orElseThrow(MissingJobDataSourceConfigurationException.forJob(COMPANY_INHERITANCE_JOB_NAME))
            )
        );
        this.companyInheritanceTable = getTargetSchemaUsingConfiguration(COMPANY_INHERITANCE_CONFIG_JOB_NAME, batchConfiguration, batchDataSourcesConfiguration)
            .map(CompanyInheritanceTable::new)
            .orElse(DEFAULT_COMPANY_INHERITANCE_TABLE);
    }

    @Override
    public void write(List<? extends CompanyInheritance> items) {

        log.info("Writing {} company inheritance documents", items.size());
        performBulkWrite(items);
    }

    @SuppressWarnings("resource")
    @Override
    protected Insert<CompanyInheritanceRecord> getInsertStatement() {

        return getDslContext().insertInto(companyInheritanceTable,
            DEFAULT_COMPANY_INHERITANCE_TABLE.FIELD_COMPANY_PREDECESSOR_SIREN,
            DEFAULT_COMPANY_INHERITANCE_TABLE.FIELD_COMPANY_SUCCESSOR_SIREN,
            DEFAULT_COMPANY_INHERITANCE_TABLE.FIELD_COMPANY_SUCCESSION_DATE,
            DEFAULT_COMPANY_INHERITANCE_TABLE.FIELD_COMPANY_HEADQUARTER_CHANGE,
            DEFAULT_COMPANY_INHERITANCE_TABLE.FIELD_COMPANY_ECONOMICAL_CONTINUITY,
            DEFAULT_COMPANY_INHERITANCE_TABLE.FIELD_COMPANY_PROCESSING_DATE
        ).values((String) null, null, null, null, null, null);
    }

    @Override
    protected Consumer<CompanyInheritance> bindToStatement(BatchBindStep statement) {

        return companyInheritance -> statement.bind(
            companyInheritance.getCompanyPredecessorSiren(),
            companyInheritance.getCompanySuccessorSiren(),
            companyInheritance.getCompanySuccessionDate(),
            companyInheritance.getCompanyHeaderChanged(),
            companyInheritance.getCompanyEconomicalContinuity(),
            companyInheritance.getCompanyProcessingTimestamp()
        );
    }
}
