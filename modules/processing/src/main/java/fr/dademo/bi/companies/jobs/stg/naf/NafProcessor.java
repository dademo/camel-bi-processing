package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import org.jeasy.batch.core.processor.RecordProcessor;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

@ApplicationScoped
public class NafProcessor implements RecordProcessor<NafDefinitionContainer, NafDefinition> {

    @Override
    public Record<NafDefinition> processRecord(Record<NafDefinitionContainer> item) {
        return toRecord(item.getHeader(), item.getPayload().getFields());
    }

    private Record<NafDefinition> toRecord(Header sourceHeader, NafDefinition payload) {
        return new GenericRecord<>(
                new Header(
                        sourceHeader.getNumber(),
                        sourceHeader.getSource(),
                        LocalDateTime.now()),
                payload
        );
    }
}
