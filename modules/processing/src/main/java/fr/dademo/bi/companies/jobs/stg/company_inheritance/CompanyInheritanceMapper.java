package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.entities.CompanyInheritanceEntity;
import org.apache.commons.csv.CSVRecord;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.mapper.RecordMapper;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

@ApplicationScoped
public class CompanyInheritanceMapper implements RecordMapper<CSVRecord, CompanyInheritanceEntity> {

    private static final Logger LOGGER = Logger.getLogger(CompanyInheritanceMapper.class);

    @Override
    public Record<CompanyInheritanceEntity> processRecord(Record<CSVRecord> item) {
        return toRecord(item.getHeader(), mappedToCompanyInheritanceEntity(item.getPayload()));
    }

    private Record<CompanyInheritanceEntity> toRecord(Header sourceHeader, CompanyInheritanceEntity payload) {
        return new GenericRecord<>(
                new Header(
                        sourceHeader.getNumber(),
                        sourceHeader.getSource(),
                        LocalDateTime.now()),
                payload
        );
    }

    private CompanyInheritanceEntity mappedToCompanyInheritanceEntity(CSVRecord csvRecord) {

        return CompanyInheritanceEntity.builder()
                // TODO
                .build();
    }
}
