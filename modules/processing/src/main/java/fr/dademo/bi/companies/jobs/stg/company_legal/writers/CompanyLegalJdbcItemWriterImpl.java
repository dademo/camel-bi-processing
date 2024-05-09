/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal.writers;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.bi.companies.jobs.stg.company_legal.CompanyLegalItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegal;
import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegalRecord;
import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegalTable;
import fr.dademo.bi.companies.shared.AbstractApplicationJdbcWriter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.BatchBindStep;
import org.jooq.Insert;
import org.springframework.batch.item.Chunk;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_legal.JobDefinition.COMPANY_LEGAL_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_legal.JobDefinition.COMPANY_LEGAL_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegalTable.DEFAULT_COMPANY_LEGAL_TABLE;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_LEGAL_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class CompanyLegalJdbcItemWriterImpl extends AbstractApplicationJdbcWriter<CompanyLegal, CompanyLegalRecord> implements CompanyLegalItemWriter {

    private final CompanyLegalTable companyLegalTable;

    public CompanyLegalJdbcItemWriterImpl(
        DataSourcesFactory dataSourcesFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration
    ) {

        super(
            dataSourcesFactory.getDslContextByDataSourceName(
                getJobOutputDataSourceName(COMPANY_LEGAL_CONFIG_JOB_NAME, batchConfiguration)
                    .orElseThrow(MissingJobDataSourceConfigurationException.forJob(COMPANY_LEGAL_JOB_NAME)))
        );
        this.companyLegalTable = getTargetSchemaUsingConfiguration(COMPANY_LEGAL_CONFIG_JOB_NAME, batchConfiguration, batchDataSourcesConfiguration)
            .map(CompanyLegalTable::new)
            .orElse(DEFAULT_COMPANY_LEGAL_TABLE);
    }

    @Override
    public void write(Chunk<? extends CompanyLegal> items) {

        log.info("Writing {} company legal documents", items.size());
        performBulkWrite(items);
    }

    protected Insert<CompanyLegalRecord> getInsertStatement() {

        return getDslContext().insertInto(companyLegalTable,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_SIREN,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_DIFFUSION_STATUS,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_IS_PURGED,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_CREATION_DATE,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_ACRONYM,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_SEX,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_1,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_2,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_3,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_4,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_USUAL_FIRST_NAME,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_PSEUDONYM,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_ASSOCIATION_IDENTIFIER,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_RANGE,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_YEAR,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_LAST_PROCESSING,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_PERIODS_COUNT,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY_YEAR,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_BEGIN_DATE,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_ADMINISTRATIVE_STATE,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_NAME,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_USUAL_NAME,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_DENOMINATION,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_1,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_2,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_3,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_LEGAL_CATEGORY,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_HEADQUARTERS_NIC,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY,
            DEFAULT_COMPANY_LEGAL_TABLE.FIELD_COMPANY_LEGAL_UNIT_IS_EMPLOYER
        ).values((String) null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null
        );
    }

    @Override
    protected Consumer<CompanyLegal> bindToStatement(BatchBindStep statement) {

        return companyLegal -> statement.bind(
            companyLegal.getSiren(),
            companyLegal.getDiffusionStatus(),
            companyLegal.getIsPurged(),
            companyLegal.getCreationDate(),
            companyLegal.getAcronym(),
            companyLegal.getSex(),
            companyLegal.getFirstName1(),
            companyLegal.getFirstName2(),
            companyLegal.getFirstName3(),
            companyLegal.getFirstName4(),
            companyLegal.getUsualFirstName(),
            companyLegal.getPseudonym(),
            companyLegal.getAssociationIdentifier(),
            companyLegal.getStaffNumberRange(),
            companyLegal.getStaffNumberYear(),
            companyLegal.getLastProcessing(),
            companyLegal.getPeriodsCount(),
            companyLegal.getCompanyCategory(),
            companyLegal.getCompanyCategoryYear(),
            companyLegal.getBeginDate(),
            companyLegal.getAdministrativeState(),
            companyLegal.getName(),
            companyLegal.getUsualName(),
            companyLegal.getDenomination(),
            companyLegal.getUsualDenomination1(),
            companyLegal.getUsualDenomination2(),
            companyLegal.getUsualDenomination3(),
            companyLegal.getLegalCategory(),
            companyLegal.getPrincipalActivity(),
            companyLegal.getPrincipalActivityNomenclature(),
            companyLegal.getHeadquartersNic(),
            companyLegal.getIsSocialAndSolidarityEconomy(),
            companyLegal.getIsEmployer()
        );
    }
}
