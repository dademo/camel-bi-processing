package fr.dademo.bi.companies.jobs.stg.company_history;

import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

import static fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory.*;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.toBoolean;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.toLocalDate;

@Component
public class CompanyHistoryItemMapper implements ItemProcessor<CSVRecord, CompanyHistory> {

    @Override
    public CompanyHistory process(@Nonnull CSVRecord item) {
        return mappedToCompanyHistory(item);
    }

    private CompanyHistory mappedToCompanyHistory(CSVRecord csvRecord) {

        return CompanyHistory.builder()
                .siren(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_SIREN))
                .nic(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_NIC))
                .siret(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_SIRET))
                .endDate(toLocalDate(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_END_DATE)))
                .beginDate(toLocalDate(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_BEGIN_DATE)))
                .institutionAdministrativeState(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_ADMINISTRATIVE_STATE))
                .institutionAdministrativeStateChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE)))
                .institution1Name(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_1_NAME))
                .institution2Name(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_2_NAME))
                .institution3Name(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_3_NAME))
                .institutionNameChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_NAME_CHANGE)))
                .institutionUsualName(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_USUAL_NAME))
                .institutionUsualNameChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_USUAL_NAME_CHANGE)))
                .institutionPrimaryActivity(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY))
                .institutionPrimaryActivityNomenclature(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE))
                .institutionPrimaryActivityChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY_CHANGE)))
                .institutionEmployerNature(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_EMPLOYER_NATURE))
                .institutionEmployerNatureChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_HISTORY_INSTITUTION_EMPLOYER_NATURE_CHANGE)))
                .build();
    }
}
