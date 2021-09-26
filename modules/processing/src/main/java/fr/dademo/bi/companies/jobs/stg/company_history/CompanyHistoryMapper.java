package fr.dademo.bi.companies.jobs.stg.company_history;

import fr.dademo.bi.companies.jobs.stg.company_history.entities.CompanyHistoryEntity;
import org.apache.commons.csv.CSVRecord;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.mapper.RecordMapper;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

import static fr.dademo.bi.companies.jobs.stg.company_history.entities.CompanyHistoryEntity.*;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.fromBoolean;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.toLocalDate;

@ApplicationScoped
public class CompanyHistoryMapper implements RecordMapper<CSVRecord, CompanyHistoryEntity> {

    private static final Logger LOGGER = Logger.getLogger(CompanyHistoryMapper.class);

    @Override
    public Record<CompanyHistoryEntity> processRecord(Record<CSVRecord> item) {
        return toRecord(item.getHeader(), mappedToCompanyHistoryEntity(item.getPayload()));
    }

    private Record<CompanyHistoryEntity> toRecord(Header sourceHeader, CompanyHistoryEntity payload) {
        return new GenericRecord<>(
                new Header(
                        sourceHeader.getNumber(),
                        sourceHeader.getSource(),
                        LocalDateTime.now()),
                payload
        );
    }

    private CompanyHistoryEntity mappedToCompanyHistoryEntity(CSVRecord csvRecord) {

        return CompanyHistoryEntity.builder()
                .siren(csvRecord.get(FIELD_SIREN))
                .nic(csvRecord.get(FIELD_NIC))
                .siret(csvRecord.get(FIELD_SIRET))
                .endDate(toLocalDate(csvRecord.get(FIELD_END_DATE)))
                .beginDate(toLocalDate(csvRecord.get(FIELD_BEGIN_DATE)))
                .institutionAdministrativeState(csvRecord.get(FIELD_INSTITUTION_ADMINISTRATIVE_STATE))
                .institutionAdministrativeStateChange(fromBoolean(csvRecord.get(FIELD_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE)))
                .institution1Name(csvRecord.get(FIELD_INSTITUTION_1_NAME))
                .institution2Name(csvRecord.get(FIELD_INSTITUTION_2_NAME))
                .institution3Name(csvRecord.get(FIELD_INSTITUTION_3_NAME))
                .institutionNameChange(fromBoolean(csvRecord.get(FIELD_INSTITUTION_NAME_CHANGE)))
                .institutionUsualName(csvRecord.get(FIELD_INSTITUTION_USUAL_NAME))
                .institutionUsualNameChange(fromBoolean(csvRecord.get(FIELD_INSTITUTION_USUAL_NAME_CHANGE)))
                .institutionPrimaryActivity(csvRecord.get(FIELD_INSTITUTION_PRIMARY_ACTIVITY))
                .institutionPrimaryActivityNomenclature(csvRecord.get(FIELD_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE))
                .institutionPrimaryActivityChange(fromBoolean(csvRecord.get(FIELD_INSTITUTION_PRIMARY_ACTIVITY_CHANGE)))
                .institutionEmployerNature(csvRecord.get(FIELD_INSTITUTION_EMPLOYER_NATURE))
                .institutionEmployerNatureChange(fromBoolean(csvRecord.get(FIELD_INSTITUTION_EMPLOYER_NATURE_CHANGE)))
                .build();
    }
}
