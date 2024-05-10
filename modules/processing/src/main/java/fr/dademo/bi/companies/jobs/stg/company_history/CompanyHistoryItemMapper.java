/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_history;

import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import static fr.dademo.batch.tools.batch.mapper.BatchMapperTools.toBoolean;
import static fr.dademo.batch.tools.batch.mapper.BatchMapperTools.toLocalDate;
import static fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory.*;

/**
 * @author dademo
 */
@Component
public class CompanyHistoryItemMapper implements ItemProcessor<WrappedRowResource, CompanyHistory> {

    private CompanyHistoryCsvColumnsMapping columnsIndexMapping;

    @Override
    public CompanyHistory process(@Nonnull WrappedRowResource item) {
        return mappedToCompanyHistory(item);
    }

    private CompanyHistory mappedToCompanyHistory(WrappedRowResource item) {

        if (columnsIndexMapping == null) {
            synchronized (this) {
                // Filling the mapping
                columnsIndexMapping = getHeaderMapping(item);
            }
        }

        return CompanyHistory.builder()
            .siren(item.getString(columnsIndexMapping.getSirenField()))
            .nic(item.getString(columnsIndexMapping.getNicField()))
            .siret(item.getString(columnsIndexMapping.getSiretField()))
            .endDate(toLocalDate(item.getString(columnsIndexMapping.getEndDateField())))
            .beginDate(toLocalDate(item.getString(columnsIndexMapping.getBeginDateField())))
            .institutionAdministrativeState(item.getString(columnsIndexMapping.getInstitutionAdministrativeStateField()))
            .institutionAdministrativeStateChange(toBoolean(item.getString(columnsIndexMapping.getInstitutionAdministrativeStateChangeField())))
            .institution1Name(item.getString(columnsIndexMapping.getInstitution1NameField()))
            .institution2Name(item.getString(columnsIndexMapping.getInstitution2NameField()))
            .institution3Name(item.getString(columnsIndexMapping.getInstitution3NameField()))
            .institutionNameChange(toBoolean(item.getString(columnsIndexMapping.getInstitutionNameChangeField())))
            .institutionUsualName(item.getString(columnsIndexMapping.getInstitutionUsualNameField()))
            .institutionUsualNameChange(toBoolean(item.getString(columnsIndexMapping.getInstitutionUsualNameChangeField())))
            .institutionPrimaryActivity(item.getString(columnsIndexMapping.getInstitutionPrimaryActivityField()))
            .institutionPrimaryActivityNomenclature(item.getString(columnsIndexMapping.getInstitutionPrimaryActivityNomenclatureField()))
            .institutionPrimaryActivityChange(toBoolean(item.getString(columnsIndexMapping.getInstitutionPrimaryActivityChangeField())))
            .institutionEmployerNature(item.getString(columnsIndexMapping.getInstitutionEmployerNatureField()))
            .institutionEmployerNatureChange(toBoolean(item.getString(columnsIndexMapping.getInstitutionEmployerNatureChangeField())))
            .build();
    }

    private CompanyHistoryCsvColumnsMapping getHeaderMapping(WrappedRowResource item) {

        return CompanyHistoryCsvColumnsMapping.builder()

            .sirenField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_SIREN))
            .nicField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_NIC))
            .siretField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_SIRET))
            .endDateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_END_DATE))
            .beginDateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_BEGIN_DATE))
            .institutionAdministrativeStateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_ADMINISTRATIVE_STATE))
            .institutionAdministrativeStateChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE))
            .institution1NameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_1_NAME))
            .institution2NameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_2_NAME))
            .institution3NameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_3_NAME))
            .institutionNameChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_NAME_CHANGE))
            .institutionUsualNameField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_USUAL_NAME))
            .institutionUsualNameChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_USUAL_NAME_CHANGE))
            .institutionPrimaryActivityField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY))
            .institutionPrimaryActivityNomenclatureField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE))
            .institutionPrimaryActivityChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY_CHANGE))
            .institutionEmployerNatureField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_EMPLOYER_NATURE))
            .institutionEmployerNatureChangeField(item.getColumnIndexByName(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_EMPLOYER_NATURE_CHANGE))
            .build();
    }
}
