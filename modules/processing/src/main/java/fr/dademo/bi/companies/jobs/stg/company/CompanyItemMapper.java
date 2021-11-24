/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

import static fr.dademo.bi.companies.jobs.stg.company.datamodel.Company.*;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.*;

/**
 * @author dademo
 */
@Component
public class CompanyItemMapper implements ItemProcessor<CSVRecord, Company> {

    @Override
    public Company process(@Nonnull CSVRecord item) {
        return mappedToCompanyHistory(item);
    }

    private Company mappedToCompanyHistory(CSVRecord csvRecord) {

        return Company.builder()
            .siren(csvRecord.get(CSV_FIELD_COMPANY_SIREN))
            .nic(csvRecord.get(CSV_FIELD_COMPANY_NIC))
            .siret(csvRecord.get(CSV_FIELD_COMPANY_SIRET))
            .companyDiffusionStatut(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_DIFFUSION_STATUT))
            .companyCreationDate(toLocalDate(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_CREATION_DATE)))
            .companyStaffNumberRange(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_STAFF_NUMBER_RANGE))
            .companyStaffNumberYear(toInteger(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_STAFF_NUMBER_YEAR)))
            .companyPrincipalRegisteredActivity(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_PRINCIPAL_REGISTERED_ACTIVITY))
            .companyLastProcessingTimestamp(toLocalDateTime(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_LAST_PROCESSING_DATE)))
            .companyIsHeadquarters(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_IS_HEADQUARTERS)))
            .companyPeriodCount(toInteger(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_PERIOD_COUNT)))
            .companyAddressComplement(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_COMPLEMENT))
            .companyAddressStreetNumber(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER))
            .companyAddressStreetNumberRepetition(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_REPETITION))
            .companyAddressStreetType(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_TYPE))
            .companyAddressStreetName(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NAME))
            .companyAddressPostalCode(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_POSTAL_CODE))
            .companyAddressCity(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY))
            .companyForeignAddressCity(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_CITY))
            .companyAddressSpecialDistribution(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION))
            .companyAddressCityCode(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_CODE))
            .companyAddressCedexCode(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_CODE))
            .companyAddressCedexName(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_NAME))
            .companyForeignAddressCountryCode(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE))
            .companyForeignAddressCountryName(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME))
            .companyAddressComplement2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_COMPLEMENT_2))
            .companyAddressStreetNumber2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_2))
            .companyAddressStreetNumberRepetition2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_REPETITION_2))
            .companyAddressStreetType2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_TYPE_2))
            .companyAddressStreetName2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NAME_2))
            .companyAddressPostalCode2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_POSTAL_CODE_2))
            .companyAddressCity2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_2))
            .companyForeignAddressCity2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_CITY_2))
            .companyAddressSpecialDistribution2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION_2))
            .companyAddressCityCode2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_CODE_2))
            .companyAddressCedexCode2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_CODE_2))
            .companyAddressCedexName2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_NAME_2))
            .companyForeignAddressCountryCode2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE_2))
            .companyForeignAddressCountryName2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME_2))
            .beginDate(toLocalDate(csvRecord.get(CSV_FIELD_COMPANY_BEGIN_DATE)))
            .companyAdministativeState(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ADMINISTATIVE_STATE))
            .companyName1(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_NAME_1))
            .companyName2(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_NAME_2))
            .companyName3(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_NAME_3))
            .companyUsualName(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_USUAL_NAME))
            .companyActivity(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_ACTIVITY))
            .companyPrincipalActivityName(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_PRINCIPAL_ACTIVITY_NAME))
            .companyIsEmployer(csvRecord.get(CSV_FIELD_COMPANY_COMPANY_IS_EMPLOYER))
            .build();
    }
}
