package fr.dademo.bi.companies.jobs.stg.company_legal_history;

import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import org.apache.commons.csv.CSVRecord;
import org.jeasy.batch.core.mapper.RecordMapper;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

import static fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory.*;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.toBoolean;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.toLocalDate;

@ApplicationScoped
public class CompanyLegalHistoryMapper implements RecordMapper<CSVRecord, CompanyLegalHistory> {

    @Override
    public Record<CompanyLegalHistory> processRecord(Record<CSVRecord> item) {
        return toRecord(item.getHeader(), mappedToCompanyHistory(item.getPayload()));
    }

    private Record<CompanyLegalHistory> toRecord(Header sourceHeader, CompanyLegalHistory payload) {
        return new GenericRecord<>(
                new Header(
                        sourceHeader.getNumber(),
                        sourceHeader.getSource(),
                        LocalDateTime.now()),
                payload
        );
    }

    private CompanyLegalHistory mappedToCompanyHistory(CSVRecord csvRecord) {

        return CompanyLegalHistory.builder()
                .siren(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_SIREN))
                .endDate(toLocalDate(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_END_DATE)))
                .beginDate(toLocalDate(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_BEGIN_DATE)))
                .legalUnitAdministrativeState(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE))
                .legalUnitAdministrativeStateChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE_CHANGE)))
                .legalUnitLegalUnitName(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME))
                .legalUnitLegalUnitNameChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME_CHANGE)))
                .legalUnitUsualName(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME))
                .legalUnitUsualNameChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME_CHANGE)))
                .legalUnitDenomination(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION))
                .legalUnitDenominationChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION_CHANGE)))
                .legalUnitUsualDenomination1(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_1))
                .legalUnitUsualDenomination2(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_2))
                .legalUnitUsualDenomination3(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_3))
                .legalUnitUsualDenominationChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_CHANGE)))
                .legalUnitLegalCategory(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY))
                .legalUnitLegalCategoryChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY_CHANGE)))
                .legalUnitPrincipalActivity(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY))
                .legalUnitPrincipalActivityNomenclature(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE))
                .legalUnitPrincipalActivityNomenclatureChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE_CHANGE)))
                .legalUnitHeadquarterNic(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC))
                .legalUnitHeadquarterNicChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC_CHANGE)))
                .legalUnitIsSocialAndSolidarityEconomy(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY))
                .legalUnitIsSocialAndSolidarityEconomyChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY_CHANGE)))
                .legalUnitIsEmployer(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER))
                .legalUnitIsEmployerChange(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER_CHANGE)))
                .build();
    }
}
