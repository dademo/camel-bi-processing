/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal_history;

import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

import static fr.dademo.batch.tools.batch.mapper.BatchMapperTools.toBoolean;
import static fr.dademo.batch.tools.batch.mapper.BatchMapperTools.toLocalDate;
import static fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory.*;

/**
 * @author dademo
 */
@Component
public class CompanyLegalHistoryItemMapper implements ItemProcessor<WrappedRowResource, CompanyLegalHistory> {

    private CompanyLegalHistoryCsvColumnsMapping columnsIndexMapping;

    @Override
    public CompanyLegalHistory process(@Nonnull WrappedRowResource item) {
        return mappedToCompanyLegalHistory(item);
    }

    private CompanyLegalHistory mappedToCompanyLegalHistory(WrappedRowResource item) {

        if (columnsIndexMapping == null) {
            // Filling the mapping
            columnsIndexMapping = getHeaderMapping(item);
        }

        return CompanyLegalHistory.builder()
            .siren(item.getString(columnsIndexMapping.getSirenField()))
            .endDate(toLocalDate(item.getString(columnsIndexMapping.getEndDateField())))
            .beginDate(toLocalDate(item.getString(columnsIndexMapping.getBeginDateField())))
            .legalUnitAdministrativeState(item.getString(columnsIndexMapping.getLegalUnitAdministrativeStateField()))
            .legalUnitAdministrativeStateChange(toBoolean(item.getString(columnsIndexMapping.getLegalUnitAdministrativeStateChangeField())))
            .legalUnitLegalUnitName(item.getString(columnsIndexMapping.getLegalUnitLegalUnitNameField()))
            .legalUnitLegalUnitNameChange(toBoolean(item.getString(columnsIndexMapping.getLegalUnitLegalUnitNameChangeField())))
            .legalUnitUsualName(item.getString(columnsIndexMapping.getLegalUnitUsualNameField()))
            .legalUnitUsualNameChange(toBoolean(item.getString(columnsIndexMapping.getLegalUnitUsualNameChangeField())))
            .legalUnitDenomination(item.getString(columnsIndexMapping.getLegalUnitDenominationField()))
            .legalUnitDenominationChange(toBoolean(item.getString(columnsIndexMapping.getLegalUnitDenominationChangeField())))
            .legalUnitUsualDenomination1(item.getString(columnsIndexMapping.getLegalUnitUsualDenomination1Field()))
            .legalUnitUsualDenomination2(item.getString(columnsIndexMapping.getLegalUnitUsualDenomination2Field()))
            .legalUnitUsualDenomination3(item.getString(columnsIndexMapping.getLegalUnitUsualDenomination3Field()))
            .legalUnitUsualDenominationChange(toBoolean(item.getString(columnsIndexMapping.getLegalUnitUsualDenominationChangeField())))
            .legalUnitLegalCategory(item.getString(columnsIndexMapping.getLegalUnitLegalCategoryField()))
            .legalUnitLegalCategoryChange(toBoolean(item.getString(columnsIndexMapping.getLegalUnitLegalCategoryChangeField())))
            .legalUnitPrincipalActivity(item.getString(columnsIndexMapping.getLegalUnitPrincipalActivityField()))
            .legalUnitPrincipalActivityNomenclature(item.getString(columnsIndexMapping.getLegalUnitPrincipalActivityNomenclatureField()))
            .legalUnitPrincipalActivityNomenclatureChange(toBoolean(item.getString(columnsIndexMapping.getLegalUnitPrincipalActivityNomenclatureChangeField())))
            .legalUnitHeadquarterNic(item.getString(columnsIndexMapping.getLegalUnitHeadquarterNicField()))
            .legalUnitHeadquarterNicChange(toBoolean(item.getString(columnsIndexMapping.getLegalUnitHeadquarterNicChangeField())))
            .legalUnitIsSocialAndSolidarityEconomy(item.getString(columnsIndexMapping.getLegalUnitIsSocialAndSolidarityEconomyField()))
            .legalUnitIsSocialAndSolidarityEconomyChange(toBoolean(item.getString(columnsIndexMapping.getLegalUnitIsSocialAndSolidarityEconomyChangeField())))
            .legalUnitIsEmployer(item.getString(columnsIndexMapping.getLegalUnitIsEmployerField()))
            .legalUnitIsEmployerChange(toBoolean(item.getString(columnsIndexMapping.getLegalUnitIsEmployerChangeField())))
            .build();
    }

    private CompanyLegalHistoryCsvColumnsMapping getHeaderMapping(WrappedRowResource item) {

        return CompanyLegalHistoryCsvColumnsMapping.builder()
            .sirenField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_SIREN))
            .endDateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_END_DATE))
            .beginDateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_BEGIN_DATE))
            .legalUnitAdministrativeStateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE))
            .legalUnitAdministrativeStateChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE_CHANGE))
            .legalUnitLegalUnitNameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME))
            .legalUnitLegalUnitNameChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME_CHANGE))
            .legalUnitUsualNameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME))
            .legalUnitUsualNameChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME_CHANGE))
            .legalUnitDenominationField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION))
            .legalUnitDenominationChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION_CHANGE))
            .legalUnitUsualDenomination1Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_1))
            .legalUnitUsualDenomination2Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_2))
            .legalUnitUsualDenomination3Field(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_3))
            .legalUnitUsualDenominationChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_CHANGE))
            .legalUnitLegalCategoryField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY))
            .legalUnitLegalCategoryChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY_CHANGE))
            .legalUnitPrincipalActivityField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY))
            .legalUnitPrincipalActivityNomenclatureField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE))
            .legalUnitPrincipalActivityNomenclatureChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE_CHANGE))
            .legalUnitHeadquarterNicField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC))
            .legalUnitHeadquarterNicChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC_CHANGE))
            .legalUnitIsSocialAndSolidarityEconomyField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY))
            .legalUnitIsSocialAndSolidarityEconomyChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY_CHANGE))
            .legalUnitIsEmployerField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER))
            .legalUnitIsEmployerChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER_CHANGE))
            .build();
    }
}
