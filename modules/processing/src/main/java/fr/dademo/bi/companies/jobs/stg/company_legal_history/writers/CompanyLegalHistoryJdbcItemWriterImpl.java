/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal_history.writers;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.CompanyLegalHistoryItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistoryRecord;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistoryTable;
import fr.dademo.bi.companies.shared.AbstractApplicationJdbcWriter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.BatchBindStep;
import org.jooq.Insert;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_legal_history.JobDefinition.COMPANY_LEGAL_HISTORY_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_legal_history.JobDefinition.COMPANY_LEGAL_HISTORY_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistoryTable.DEFAULT_COMPANY_LEGAL_HISTORY_TABLE;

/**
 * @author dademo
 */
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_LEGAL_HISTORY_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class CompanyLegalHistoryJdbcItemWriterImpl extends AbstractApplicationJdbcWriter<CompanyLegalHistory, CompanyLegalHistoryRecord> implements CompanyLegalHistoryItemWriter {

    private final CompanyLegalHistoryTable companyLegalHistoryTable;

    public CompanyLegalHistoryJdbcItemWriterImpl(
        DataSourcesFactory dataSourcesFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration
    ) {

        super(
            dataSourcesFactory.getJobOutputDslContextByDataSourceName(
                getJobOutputDataSourceName(COMPANY_LEGAL_HISTORY_CONFIG_JOB_NAME, batchConfiguration)
                    .orElseThrow(MissingJobDataSourceConfigurationException.forJob(COMPANY_LEGAL_HISTORY_JOB_NAME)))
        );
        this.companyLegalHistoryTable = getTargetSchemaUsingConfiguration(COMPANY_LEGAL_HISTORY_CONFIG_JOB_NAME, batchConfiguration, batchDataSourcesConfiguration)
            .map(CompanyLegalHistoryTable::new)
            .orElse(DEFAULT_COMPANY_LEGAL_HISTORY_TABLE);
    }

    @Override
    public void write(List<? extends CompanyLegalHistory> items) {

        log.info("Writing {} company legal history documents", items.size());
        performBulkWrite(items);
    }

    @SuppressWarnings("resource")
    protected Insert<CompanyLegalHistoryRecord> getInsertStatement() {

        return getDslContext().insertInto(companyLegalHistoryTable,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_SIREN,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_END_DATE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_BEGIN_DATE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE_CHANGE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME_CHANGE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME_CHANGE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION_CHANGE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_1,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_2,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_3,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_CHANGE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY_CHANGE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE_CHANGE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC_CHANGE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY_CHANGE,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER,
            DEFAULT_COMPANY_LEGAL_HISTORY_TABLE.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER_CHANGE
        ).values((String) null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null
        );
    }

    @Override
    protected Consumer<CompanyLegalHistory> bindToStatement(BatchBindStep statement) {

        return companyLegalHistory -> statement.bind(
            companyLegalHistory.getSiren(),
            companyLegalHistory.getEndDate(),
            companyLegalHistory.getBeginDate(),
            companyLegalHistory.getLegalUnitAdministrativeState(),
            companyLegalHistory.getLegalUnitAdministrativeStateChange(),
            companyLegalHistory.getLegalUnitLegalUnitName(),
            companyLegalHistory.getLegalUnitLegalUnitNameChange(),
            companyLegalHistory.getLegalUnitUsualName(),
            companyLegalHistory.getLegalUnitUsualNameChange(),
            companyLegalHistory.getLegalUnitDenomination(),
            companyLegalHistory.getLegalUnitDenominationChange(),
            companyLegalHistory.getLegalUnitUsualDenomination1(),
            companyLegalHistory.getLegalUnitUsualDenomination2(),
            companyLegalHistory.getLegalUnitUsualDenomination3(),
            companyLegalHistory.getLegalUnitUsualDenominationChange(),
            companyLegalHistory.getLegalUnitLegalCategory(),
            companyLegalHistory.getLegalUnitLegalCategoryChange(),
            companyLegalHistory.getLegalUnitPrincipalActivity(),
            companyLegalHistory.getLegalUnitPrincipalActivityNomenclature(),
            companyLegalHistory.getLegalUnitPrincipalActivityNomenclatureChange(),
            companyLegalHistory.getLegalUnitHeadquarterNic(),
            companyLegalHistory.getLegalUnitHeadquarterNicChange(),
            companyLegalHistory.getLegalUnitIsSocialAndSolidarityEconomy(),
            companyLegalHistory.getLegalUnitIsSocialAndSolidarityEconomyChange(),
            companyLegalHistory.getLegalUnitIsEmployer(),
            companyLegalHistory.getLegalUnitIsEmployerChange()
        );
    }
}
