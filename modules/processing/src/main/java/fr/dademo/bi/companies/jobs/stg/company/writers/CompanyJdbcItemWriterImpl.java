/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company.writers;

import fr.dademo.bi.companies.jobs.stg.company.CompanyItemWriter;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
import lombok.Getter;
import lombok.SneakyThrows;
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
import static fr.dademo.bi.companies.jobs.stg.company.JobDefinition.COMPANY_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company.datamodel.CompanyTable.COMPANY;

/**
 * @author dademo
 */
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class CompanyJdbcItemWriterImpl implements CompanyItemWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyJdbcItemWriterImpl.class);

    @Autowired
    @Qualifier(STG_DATASOURCE_DSL_CONTEXT_BEAN_NAME)
    @Getter
    private DSLContext dslContext;

    @SneakyThrows
    @Override
    public void write(List<? extends Company> items) {

        LOGGER.info("Writing {} company documents", items.size());

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(COMPANY,
            COMPANY.FIELD_SIREN,
            COMPANY.FIELD_NIC,
            COMPANY.FIELD_SIRET,
            COMPANY.FIELD_COMPANY_DIFFUSION_STATUT,
            COMPANY.FIELD_COMPANY_CREATION_DATE,
            COMPANY.FIELD_COMPANY_STAFF_NUMBER_RANGE,
            COMPANY.FIELD_COMPANY_STAFF_NUMBER_YEAR,
            COMPANY.FIELD_COMPANY_PRINCIPAL_REGISTERED_ACTIVITY,
            COMPANY.FIELD_COMPANY_LAST_PROCESSING_DATE,
            COMPANY.FIELD_COMPANY_IS_HEADQUARTERS,
            COMPANY.FIELD_COMPANY_PERIOD_COUNT,
            COMPANY.FIELD_COMPANY_ADDRESS_COMPLEMENT,
            COMPANY.FIELD_COMPANY_ADDRESS_STREET_NUMBER,
            COMPANY.FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION,
            COMPANY.FIELD_COMPANY_ADDRESS_STREET_TYPE,
            COMPANY.FIELD_COMPANY_ADDRESS_STREET_NAME,
            COMPANY.FIELD_COMPANY_ADDRESS_POSTAL_CODE,
            COMPANY.FIELD_COMPANY_ADDRESS_CITY,
            COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_CITY,
            COMPANY.FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION,
            COMPANY.FIELD_COMPANY_ADDRESS_CITY_CODE,
            COMPANY.FIELD_COMPANY_ADDRESS_CEDEX_CODE,
            COMPANY.FIELD_COMPANY_ADDRESS_CEDEX_NAME,
            COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE,
            COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME,
            COMPANY.FIELD_COMPANY_ADDRESS_COMPLEMENT_2,
            COMPANY.FIELD_COMPANY_ADDRESS_STREET_NUMBER_2,
            COMPANY.FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION_2,
            COMPANY.FIELD_COMPANY_ADDRESS_STREET_TYPE_2,
            COMPANY.FIELD_COMPANY_ADDRESS_STREET_NAME_2,
            COMPANY.FIELD_COMPANY_ADDRESS_POSTAL_CODE_2,
            COMPANY.FIELD_COMPANY_ADDRESS_CITY_2,
            COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_CITY_2,
            COMPANY.FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION_2,
            COMPANY.FIELD_COMPANY_ADDRESS_CITY_CODE_2,
            COMPANY.FIELD_COMPANY_ADDRESS_CEDEX_CODE_2,
            COMPANY.FIELD_COMPANY_ADDRESS_CEDEX_NAME_2,
            COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE_2,
            COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME_2,
            COMPANY.FIELD_BEGIN_DATE,
            COMPANY.FIELD_COMPANY_ADMINISTATIVE_STATE,
            COMPANY.FIELD_COMPANY_NAME_1,
            COMPANY.FIELD_COMPANY_NAME_2,
            COMPANY.FIELD_COMPANY_NAME_3,
            COMPANY.FIELD_COMPANY_USUAL_NAME,
            COMPANY.FIELD_COMPANY_ACTIVITY,
            COMPANY.FIELD_COMPANY_PRINCIPAL_ACTIVITY_NAME,
            COMPANY.FIELD_COMPANY_IS_EMPLOYER
        ).values((String) null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null
        ));

        items.stream()
            .map(this::companyBind)
            .forEach(consumer -> consumer.accept(batchInsertStatement));

        final var batchResult = batchInsertStatement.execute();
        if (batchResult.length > 0) {
            final int totalUpdated = Arrays.stream(batchResult).sum();
            LOGGER.info("{} rows affected", totalUpdated);
        } else {
            LOGGER.error("An error occurred while running batch");
        }
    }

    private Consumer<BatchBindStep> companyBind(Company company) {

        return items -> items.bind(
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
