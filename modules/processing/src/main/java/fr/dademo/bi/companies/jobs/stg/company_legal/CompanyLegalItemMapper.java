/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal;

import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegal;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Optional;

import static fr.dademo.batch.tools.batch.mapper.BatchMapperTools.*;
import static fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegal.*;

/**
 * @author dademo
 */
@Component
public class CompanyLegalItemMapper implements ItemProcessor<WrappedRowResource, CompanyLegal> {

    private CompanyLegalCsvColumnsMapping columnsIndexMapping;

    @Override
    public CompanyLegal process(@Nonnull WrappedRowResource item) {
        return mappedToCompanyLegal(item);
    }

    private CompanyLegal mappedToCompanyLegal(WrappedRowResource item) {

        if (columnsIndexMapping == null) {
            synchronized (this) {
                // Filling the mapping
                columnsIndexMapping = getHeaderMapping(item);
            }
        }

        return CompanyLegal.builder()
            .siren(item.getString(columnsIndexMapping.getSirenField()))
            .diffusionStatus(item.getString(columnsIndexMapping.getDiffusionStatusField()))
            .isPurged(Optional.ofNullable(toBoolean(item.getString(columnsIndexMapping.getIsPurgedField()))).orElse(false))
            .creationDate(toLocalDate(item.getString(columnsIndexMapping.getCreationDateField())))
            .acronym(item.getString(columnsIndexMapping.getAcronymField()))
            .sex(item.getString(columnsIndexMapping.getSexField()))
            .firstName1(item.getString(columnsIndexMapping.getFirstName1Field()))
            .firstName2(item.getString(columnsIndexMapping.getFirstName2Field()))
            .firstName3(item.getString(columnsIndexMapping.getFirstName3Field()))
            .firstName4(item.getString(columnsIndexMapping.getFirstName4Field()))
            .usualFirstName(item.getString(columnsIndexMapping.getUsualFirstNameField()))
            .pseudonym(item.getString(columnsIndexMapping.getPseudonymField()))
            .associationIdentifier(item.getString(columnsIndexMapping.getAssociationIdentifierField()))
            .staffNumberRange(item.getString(columnsIndexMapping.getStaffNumberRangeField()))
            .staffNumberYear(toInteger(item.getString(columnsIndexMapping.getStaffNumberYearField())))
            .lastProcessing(toLocalDateTime(item.getString(columnsIndexMapping.getLastProcessingField())))
            .periodsCount(toInteger(item.getString(columnsIndexMapping.getPeriodsCountField())))
            .companyCategory(item.getString(columnsIndexMapping.getCompanyCategoryField()))
            .companyCategoryYear(toInteger(item.getString(columnsIndexMapping.getCompanyCategoryYearField())))
            .beginDate(toLocalDate(item.getString(columnsIndexMapping.getBeginDateField())))
            .administrativeState(item.getString(columnsIndexMapping.getAdministrativeStateField()))
            .name(item.getString(columnsIndexMapping.getNameField()))
            .usualName(item.getString(columnsIndexMapping.getUsualNameField()))
            .denomination(item.getString(columnsIndexMapping.getDenominationField()))
            .usualDenomination1(item.getString(columnsIndexMapping.getUsualDenomination1Field()))
            .usualDenomination2(item.getString(columnsIndexMapping.getUsualDenomination2Field()))
            .usualDenomination3(item.getString(columnsIndexMapping.getUsualDenomination3Field()))
            .legalCategory(item.getString(columnsIndexMapping.getLegalCategoryField()))
            .principalActivity(item.getString(columnsIndexMapping.getPrincipalActivityField()))
            .principalActivityNomenclature(item.getString(columnsIndexMapping.getPrincipalActivityNomenclatureField()))
            .headquartersNic(item.getString(columnsIndexMapping.getHeadquartersNicField()))
            .isSocialAndSolidarityEconomy(item.getString(columnsIndexMapping.getIsSocialAndSolidarityEconomyField()))
            .isEmployer(item.getString(columnsIndexMapping.getIsEmployerField()))
            .build();
    }

    private CompanyLegalCsvColumnsMapping getHeaderMapping(WrappedRowResource item) {

        return CompanyLegalCsvColumnsMapping.builder()
            .sirenField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_SIREN))
            .diffusionStatusField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_DIFFUSION_STATUS))
            .isPurgedField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_IS_PURGED))
            .creationDateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_CREATION_DATE))
            .acronymField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_ACRONYM))
            .sexField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_SEX))
            .firstName1Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_1))
            .firstName2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_2))
            .firstName3Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_3))
            .firstName4Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_4))
            .usualFirstNameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_FIRST_NAME))
            .pseudonymField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_PSEUDONYM))
            .associationIdentifierField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_ASSOCIATION_IDENTIFIER))
            .staffNumberRangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_RANGE))
            .staffNumberYearField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_YEAR))
            .lastProcessingField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_LAST_PROCESSING))
            .periodsCountField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_PERIODS_COUNT))
            .companyCategoryField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY))
            .companyCategoryYearField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY_YEAR))
            .beginDateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_BEGIN_DATE))
            .administrativeStateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_ADMINISTRATIVE_STATE))
            .nameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_NAME))
            .usualNameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_NAME))
            .denominationField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_DENOMINATION))
            .usualDenomination1Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_1))
            .usualDenomination2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_2))
            .usualDenomination3Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_3))
            .legalCategoryField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_LEGAL_CATEGORY))
            .principalActivityField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY))
            .principalActivityNomenclatureField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE))
            .headquartersNicField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_HEADQUARTERS_NIC))
            .isSocialAndSolidarityEconomyField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY))
            .isEmployerField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_UNIT_IS_EMPLOYER))
            .build();
    }
}
