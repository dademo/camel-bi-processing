/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import static fr.dademo.batch.tools.batch.mapper.BatchMapperTools.*;
import static fr.dademo.bi.companies.jobs.stg.company.datamodel.Company.*;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Component
public class CompanyItemMapper implements ItemProcessor<WrappedRowResource, Company> {

    private CompanyCsvColumnsMapping columnsIndexMapping;

    @Override
    public Company process(@Nonnull WrappedRowResource item) {
        return mappedToCompany(item);
    }

    private Company mappedToCompany(WrappedRowResource item) {

        if (columnsIndexMapping == null) {
            synchronized (this) {
                // Filling the mapping
                columnsIndexMapping = getHeaderMapping(item);
            }
        }

        return Company.builder()
            .siren(item.getString(columnsIndexMapping.getSirenField()))
            .nic(item.getString(columnsIndexMapping.getNicField()))
            .siret(item.getString(columnsIndexMapping.getSiretField()))
            .companyDiffusionStatut(item.getString(columnsIndexMapping.getCompanyDiffusionStatutField()))
            .companyCreationDate(toLocalDate(item.getString(columnsIndexMapping.getCompanyCreationDateField())))
            .companyStaffNumberRange(item.getString(columnsIndexMapping.getCompanyStaffNumberRangeField()))
            .companyStaffNumberYear(toInteger(item.getString(columnsIndexMapping.getCompanyStaffNumberYearField())))
            .companyPrincipalRegisteredActivity(item.getString(columnsIndexMapping.getCompanyPrincipalRegisteredActivityField()))
            .companyLastProcessingTimestamp(toLocalDateTime(item.getString(columnsIndexMapping.getCompanyLastProcessingTimestampField())))
            .companyIsHeadquarters(toBoolean(item.getString(columnsIndexMapping.getCompanyIsHeadquartersField())))
            .companyPeriodCount(toInteger(item.getString(columnsIndexMapping.getCompanyPeriodCountField())))
            .companyAddressComplement(item.getString(columnsIndexMapping.getCompanyAddressComplementField()))
            .companyAddressStreetNumber(item.getString(columnsIndexMapping.getCompanyAddressStreetNumberField()))
            .companyAddressStreetNumberRepetition(item.getString(columnsIndexMapping.getCompanyAddressStreetNumberRepetitionField()))
            .companyAddressStreetNumberRepetition(item.getString(columnsIndexMapping.getCompanyAddressStreetNumberRepetitionField()))
            .companyAddressStreetNumberRepetition(item.getString(columnsIndexMapping.getCompanyAddressStreetNumberRepetitionField()))
            .companyAddressStreetType(item.getString(columnsIndexMapping.getCompanyAddressStreetTypeField()))
            .companyAddressStreetName(item.getString(columnsIndexMapping.getCompanyAddressStreetNameField()))
            .companyAddressPostalCode(item.getString(columnsIndexMapping.getCompanyAddressPostalCodeField()))
            .companyAddressCity(item.getString(columnsIndexMapping.getCompanyAddressCityField()))
            .companyForeignAddressCity(item.getString(columnsIndexMapping.getCompanyForeignAddressCityField()))
            .companyAddressSpecialDistribution(item.getString(columnsIndexMapping.getCompanyAddressSpecialDistributionField()))
            .companyAddressCityCode(item.getString(columnsIndexMapping.getCompanyAddressCityCodeField()))
            .companyAddressCedexCode(item.getString(columnsIndexMapping.getCompanyAddressCedexCodeField()))
            .companyAddressCedexName(item.getString(columnsIndexMapping.getCompanyAddressCedexNameField()))
            .companyForeignAddressCountryCode(item.getString(columnsIndexMapping.getCompanyForeignAddressCountryCodeField()))
            .companyForeignAddressCountryName(item.getString(columnsIndexMapping.getCompanyForeignAddressCountryNameField()))
            .companyAddressComplement2(item.getString(columnsIndexMapping.getCompanyAddressComplement2Field()))
            .companyAddressStreetNumber2(item.getString(columnsIndexMapping.getCompanyAddressStreetNumber2Field()))
            .companyAddressStreetNumberRepetition2(item.getString(columnsIndexMapping.getCompanyAddressStreetNumberRepetition2Field()))
            .companyAddressStreetType2(item.getString(columnsIndexMapping.getCompanyAddressStreetType2Field()))
            .companyAddressStreetName2(item.getString(columnsIndexMapping.getCompanyAddressStreetName2Field()))
            .companyAddressPostalCode2(item.getString(columnsIndexMapping.getCompanyAddressPostalCode2Field()))
            .companyAddressCity2(item.getString(columnsIndexMapping.getCompanyAddressCity2Field()))
            .companyForeignAddressCity2(item.getString(columnsIndexMapping.getCompanyForeignAddressCity2Field()))
            .companyAddressSpecialDistribution2(item.getString(columnsIndexMapping.getCompanyAddressSpecialDistribution2Field()))
            .companyAddressCityCode2(item.getString(columnsIndexMapping.getCompanyAddressCityCode2Field()))
            .companyAddressCedexCode2(item.getString(columnsIndexMapping.getCompanyAddressCedexCode2Field()))
            .companyAddressCedexName2(item.getString(columnsIndexMapping.getCompanyAddressCedexName2Field()))
            .companyForeignAddressCountryCode2(item.getString(columnsIndexMapping.getCompanyForeignAddressCountryCode2Field()))
            .companyForeignAddressCountryName2(item.getString(columnsIndexMapping.getCompanyForeignAddressCountryName2Field()))
            .beginDate(toLocalDate(item.getString(columnsIndexMapping.getBeginDateField())))
            .companyAdministativeState(item.getString(columnsIndexMapping.getCompanyAdministativeStateField()))
            .companyName1(item.getString(columnsIndexMapping.getCompanyName1Field()))
            .companyName2(item.getString(columnsIndexMapping.getCompanyName2Field()))
            .companyName3(item.getString(columnsIndexMapping.getCompanyName3Field()))
            .companyUsualName(item.getString(columnsIndexMapping.getCompanyUsualNameField()))
            .companyActivity(item.getString(columnsIndexMapping.getCompanyActivityField()))
            .companyPrincipalActivityName(item.getString(columnsIndexMapping.getCompanyPrincipalActivityNameField()))
            .companyIsEmployer(item.getString(columnsIndexMapping.getCompanyIsEmployerField()))
            .build();
    }

    private CompanyCsvColumnsMapping getHeaderMapping(WrappedRowResource item) {

        return CompanyCsvColumnsMapping.builder()
            .sirenField(item.getColumnIndexByName(CSV_FIELD_COMPANY_SIREN))
            .nicField(item.getColumnIndexByName(CSV_FIELD_COMPANY_NIC))
            .siretField(item.getColumnIndexByName(CSV_FIELD_COMPANY_SIRET))
            .companyDiffusionStatutField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_DIFFUSION_STATUS))
            .companyCreationDateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_CREATION_DATE))
            .companyStaffNumberRangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_STAFF_NUMBER_RANGE))
            .companyStaffNumberYearField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_STAFF_NUMBER_YEAR))
            .companyPrincipalRegisteredActivityField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_PRINCIPAL_REGISTERED_ACTIVITY))
            .companyLastProcessingTimestampField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_LAST_PROCESSING_DATE))
            .companyIsHeadquartersField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_IS_HEADQUARTERS))
            .companyPeriodCountField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_PERIOD_COUNT))
            .companyAddressComplementField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_COMPLEMENT))
            .companyAddressStreetNumberField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER))
            .companyAddressStreetNumberRepetitionField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_REPETITION))
            .companyCompanyLastStreetNumberField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_LAST_STREET_NUMBER))
            .companyIndexRepetitionLastStreetNumberField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_INDEX_REPETITION_LAST_STREET_NUMBER))
            .companyAddressStreetTypeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_TYPE))
            .companyAddressStreetNameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NAME))
            .companyAddressPostalCodeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_POSTAL_CODE))
            .companyAddressCityField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY))
            .companyForeignAddressCityField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_CITY))
            .companyAddressSpecialDistributionField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION))
            .companyAddressCityCodeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_CODE))
            .companyAddressCedexCodeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_CODE))
            .companyAddressCedexNameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_NAME))
            .companyForeignAddressCountryCodeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE))
            .companyForeignAddressCountryNameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME))
            .companyAddressIdentifierField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_IDENTIFIER))
            .companyCoordinatesLambertAbscissaField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_COORDINATES_LAMBERT_ABSCISSA))
            .companyCoordinatesLambertOrdinateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_COORDINATES_LAMBERT_ORDINATE))
            .companyAddressComplement2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_COMPLEMENT_2))
            .companyAddressStreetNumber2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_2))
            .companyAddressStreetNumberRepetition2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_REPETITION_2))
            .companyAddressStreetType2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_TYPE_2))
            .companyAddressStreetName2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NAME_2))
            .companyAddressPostalCode2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_POSTAL_CODE_2))
            .companyAddressCity2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_2))
            .companyForeignAddressCity2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_CITY_2))
            .companyAddressSpecialDistribution2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION_2))
            .companyAddressCityCode2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_CODE_2))
            .companyAddressCedexCode2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_CODE_2))
            .companyAddressCedexName2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_NAME_2))
            .companyForeignAddressCountryCode2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE_2))
            .companyForeignAddressCountryName2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME_2))
            .beginDateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_BEGIN_DATE))
            .companyAdministativeStateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ADMINISTATIVE_STATE))
            .companyName1Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_NAME_1))
            .companyName2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_NAME_2))
            .companyName3Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_NAME_3))
            .companyUsualNameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_USUAL_NAME))
            .companyActivityField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_ACTIVITY))
            .companyPrincipalActivityNameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_PRINCIPAL_ACTIVITY_NAME))
            .companyIsEmployerField(item.getColumnIndexByName(CSV_FIELD_COMPANY_COMPANY_IS_EMPLOYER))
            .build();
    }
}
