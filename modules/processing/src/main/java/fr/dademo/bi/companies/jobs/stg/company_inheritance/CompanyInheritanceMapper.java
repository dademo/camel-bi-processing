package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import org.apache.commons.csv.CSVRecord;
import org.jeasy.batch.core.mapper.RecordMapper;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

import static fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance.*;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.*;

@ApplicationScoped
public class CompanyInheritanceMapper implements RecordMapper<CSVRecord, CompanyInheritance> {

    @Override
    public Record<CompanyInheritance> processRecord(Record<CSVRecord> item) {
        return toRecord(item.getHeader(), mappedToCompanyInheritance(item.getPayload()));
    }

    private Record<CompanyInheritance> toRecord(Header sourceHeader, CompanyInheritance payload) {
        return new GenericRecord<>(
                new Header(
                        sourceHeader.getNumber(),
                        sourceHeader.getSource(),
                        LocalDateTime.now()),
                payload
        );
    }

    private CompanyInheritance mappedToCompanyInheritance(CSVRecord csvRecord) {

        return CompanyInheritance.builder()
                .companyPredecessorSiren(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_PREDECESSOR_SIREN))
                .companySuccessorSiren(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_SUCCESSOR_SIREN))
                .companySuccessionDate(toLocalDate(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_SUCCESSION_DATE)))
                .companyHeaderChanged(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_HEADQUARTER_CHANGE)))
                .companyEconomicalContinuity(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_ECONOMICAL_CONTINUITY)))
                .companyProcessingTimestamp(toLocalDateTime(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_PROCESSING_DATE)))
                .build();
    }
}
