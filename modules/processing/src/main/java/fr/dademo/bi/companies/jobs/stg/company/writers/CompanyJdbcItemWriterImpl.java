/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company.writers;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.bi.companies.jobs.stg.company.CompanyItemWriter;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.CompanyRecord;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.CompanyTable;
import fr.dademo.bi.companies.shared.AbstractApplicationJdbcWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.BatchBindStep;
import org.jooq.Insert;
import org.springframework.batch.item.Chunk;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company.JobDefinition.COMPANY_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company.JobDefinition.COMPANY_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company.datamodel.CompanyTable.DEFAULT_COMPANY_TABLE;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class CompanyJdbcItemWriterImpl extends AbstractApplicationJdbcWriter<Company, CompanyRecord> implements CompanyItemWriter {

    private final CompanyTable companyTable;

    public CompanyJdbcItemWriterImpl(
        DataSourcesFactory dataSourcesFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration
    ) {

        super(
            dataSourcesFactory.getDslContextByDataSourceName(
                getJobOutputDataSourceName(COMPANY_CONFIG_JOB_NAME, batchConfiguration)
                    .orElseThrow(MissingJobDataSourceConfigurationException.forJob(COMPANY_JOB_NAME))
            )
        );
        this.companyTable = getTargetSchemaUsingConfiguration(COMPANY_CONFIG_JOB_NAME, batchConfiguration, batchDataSourcesConfiguration)
            .map(CompanyTable::new)
            .orElse(DEFAULT_COMPANY_TABLE);
    }

    @SneakyThrows
    @Override
    public void write(Chunk<? extends Company> items) {

        log.info("Writing {} company documents", items.size());
        performBulkWrite(items);
    }

    @Override
    protected Insert<CompanyRecord> getInsertStatement() {

        return getDslContext().insertInto(companyTable,
            DEFAULT_COMPANY_TABLE.FIELD_SIREN,
            DEFAULT_COMPANY_TABLE.FIELD_NIC,
            DEFAULT_COMPANY_TABLE.FIELD_SIRET,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_DIFFUSION_STATUT,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_CREATION_DATE,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_STAFF_NUMBER_RANGE,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_STAFF_NUMBER_YEAR,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_PRINCIPAL_REGISTERED_ACTIVITY,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_LAST_PROCESSING_DATE,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_IS_HEADQUARTERS,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_PERIOD_COUNT,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_COMPLEMENT,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_STREET_NUMBER,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_STREET_TYPE,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_STREET_NAME,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_POSTAL_CODE,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_CITY,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_FOREIGN_ADDRESS_CITY,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_CITY_CODE,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_CEDEX_CODE,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_CEDEX_NAME,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_COMPLEMENT_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_STREET_NUMBER_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_STREET_TYPE_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_STREET_NAME_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_POSTAL_CODE_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_CITY_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_FOREIGN_ADDRESS_CITY_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_CITY_CODE_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_CEDEX_CODE_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADDRESS_CEDEX_NAME_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME_2,
            DEFAULT_COMPANY_TABLE.FIELD_BEGIN_DATE,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ADMINISTATIVE_STATE,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_NAME_1,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_NAME_2,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_NAME_3,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_USUAL_NAME,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_ACTIVITY,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_PRINCIPAL_ACTIVITY_NAME,
            DEFAULT_COMPANY_TABLE.FIELD_COMPANY_IS_EMPLOYER
        ).values((String) null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null
        );
    }

    @Override
    protected Consumer<Company> bindToStatement(BatchBindStep statement) {

        return company -> statement.bind(
            company.getSiren(),
            company.getNic(),
            company.getSiret(),
            company.getCompanyDiffusionStatut(),
            company.getCompanyCreationDate(),
            company.getCompanyStaffNumberRange(),
            company.getCompanyStaffNumberYear(),
            company.getCompanyPrincipalRegisteredActivity(),
            company.getCompanyLastProcessingTimestamp(),
            company.getCompanyIsHeadquarters(),
            company.getCompanyPeriodCount(),
            company.getCompanyAddressComplement(),
            company.getCompanyAddressStreetNumber(),
            company.getCompanyAddressStreetNumberRepetition(),
            company.getCompanyAddressStreetType(),
            company.getCompanyAddressStreetName(),
            company.getCompanyAddressPostalCode(),
            company.getCompanyAddressCity(),
            company.getCompanyForeignAddressCity(),
            company.getCompanyAddressSpecialDistribution(),
            company.getCompanyAddressCityCode(),
            company.getCompanyAddressCedexCode(),
            company.getCompanyAddressCedexName(),
            company.getCompanyForeignAddressCountryCode(),
            company.getCompanyForeignAddressCountryName(),
            company.getCompanyAddressComplement2(),
            company.getCompanyAddressStreetNumberRepetition2(),
            company.getCompanyAddressStreetNumberRepetition2(),
            company.getCompanyAddressStreetType2(),
            company.getCompanyAddressStreetName2(),
            company.getCompanyAddressPostalCode2(),
            company.getCompanyAddressCity2(),
            company.getCompanyForeignAddressCity2(),
            company.getCompanyAddressSpecialDistribution2(),
            company.getCompanyAddressCityCode2(),
            company.getCompanyAddressCedexCode2(),
            company.getCompanyAddressCedexName2(),
            company.getCompanyForeignAddressCountryCode2(),
            company.getCompanyForeignAddressCountryName2(),
            company.getBeginDate(),
            company.getCompanyAdministativeState(),
            company.getCompanyName1(),
            company.getCompanyName2(),
            company.getCompanyName3(),
            company.getCompanyUsualName(),
            company.getCompanyActivity(),
            company.getCompanyPrincipalActivityName(),
            company.getCompanyIsEmployer()
        );
    }
}
