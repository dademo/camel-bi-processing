package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import org.apache.commons.csv.CSVRecord;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.mapper.RecordMapper;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

@ApplicationScoped
public class CompanyInheritanceMapper implements RecordMapper<CSVRecord, CompanyInheritance> {

    private static final Logger LOGGER = Logger.getLogger(CompanyInheritanceMapper.class);

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
                // TODO
                .build();
    }
}
