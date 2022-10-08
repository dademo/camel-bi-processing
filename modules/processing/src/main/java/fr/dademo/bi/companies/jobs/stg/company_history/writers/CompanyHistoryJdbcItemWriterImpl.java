/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_history.writers;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.bi.companies.jobs.stg.company_history.CompanyHistoryItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistoryRecord;
import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistoryTable;
import fr.dademo.bi.companies.shared.AbstractApplicationJdbcWriter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.BatchBindStep;
import org.jooq.Insert;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_history.JobDefinition.COMPANY_HISTORY_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_history.JobDefinition.COMPANY_HISTORY_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistoryTable.DEFAULT_COMPANY_HISTORY_TABLE;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_HISTORY_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class CompanyHistoryJdbcItemWriterImpl extends AbstractApplicationJdbcWriter<CompanyHistory, CompanyHistoryRecord> implements CompanyHistoryItemWriter {

    private final CompanyHistoryTable companyHistoryTable;

    public CompanyHistoryJdbcItemWriterImpl(
        DataSourcesFactory dataSourcesFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration
    ) {

        super(
            dataSourcesFactory.getDslContextByDataSourceName(
                getJobOutputDataSourceName(COMPANY_HISTORY_CONFIG_JOB_NAME, batchConfiguration)
                    .orElseThrow(MissingJobDataSourceConfigurationException.forJob(COMPANY_HISTORY_JOB_NAME))
            )
        );
        this.companyHistoryTable = getTargetSchemaUsingConfiguration(COMPANY_HISTORY_CONFIG_JOB_NAME, batchConfiguration, batchDataSourcesConfiguration)
            .map(CompanyHistoryTable::new)
            .orElse(DEFAULT_COMPANY_HISTORY_TABLE);
    }

    @Override
    public void write(List<? extends CompanyHistory> items) {

        log.info("Writing {} company history documents", items.size());
        performBulkWrite(items);
    }

    @SuppressWarnings("resource")
    @Override
    protected Insert<CompanyHistoryRecord> getInsertStatement() {

        return getDslContext().insertInto(companyHistoryTable,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_SIREN,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_NIC,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_SIRET,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_END_DATE,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_BEGIN_DATE,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_ADMINISTRATIVE_STATE,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_1_NAME,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_2_NAME,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_3_NAME,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_NAME_CHANGE,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_USUAL_NAME,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_USUAL_NAME_CHANGE,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_PRIMARY_ACTIVITY,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_PRIMARY_ACTIVITY_CHANGE,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_EMPLOYER_NATURE,
            DEFAULT_COMPANY_HISTORY_TABLE.FIELD_INSTITUTION_EMPLOYER_NATURE_CHANGE
        ).values((String) null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null
        );
    }

    @Override
    protected Consumer<CompanyHistory> bindToStatement(BatchBindStep statement) {

        return companyHistory -> statement.bind(
            companyHistory.getSiren(),
            companyHistory.getNic(),
            companyHistory.getSiret(),
            companyHistory.getEndDate(),
            companyHistory.getBeginDate(),
            companyHistory.getInstitutionAdministrativeState(),
            companyHistory.getInstitutionAdministrativeStateChange(),
            companyHistory.getInstitution1Name(),
            companyHistory.getInstitution2Name(),
            companyHistory.getInstitution3Name(),
            companyHistory.getInstitutionNameChange(),
            companyHistory.getInstitutionUsualName(),
            companyHistory.getInstitutionUsualNameChange(),
            companyHistory.getInstitutionPrimaryActivity(),
            companyHistory.getInstitutionPrimaryActivityNomenclature(),
            companyHistory.getInstitutionPrimaryActivityChange(),
            companyHistory.getInstitutionEmployerNature(),
            companyHistory.getInstitutionEmployerNatureChange()
        );
    }
}
