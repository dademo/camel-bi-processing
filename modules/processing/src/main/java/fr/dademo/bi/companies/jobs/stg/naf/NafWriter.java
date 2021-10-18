package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.tools.batch.writer.BatchWriterTools;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.record.Batch;
import org.jeasy.batch.core.record.Record;
import org.jeasy.batch.core.writer.RecordWriter;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.jooq.Field;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.function.Consumer;

import static fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionTable.NAF_DEFINITION;
import static fr.dademo.bi.companies.tools.DefaultAppBeans.STG_DSL_CONTEXT;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

@ApplicationScoped
public class NafWriter implements RecordWriter<NafDefinition> {

    private static final Logger LOGGER = Logger.getLogger(NafWriter.class);

    @Inject
    @Named(STG_DSL_CONTEXT)
    DSLContext dslContext;

    @SneakyThrows
    @Override
    public void writeRecords(Batch<NafDefinition> batch) {

        LOGGER.info(String.format("Writing %d naf definition documents", batch.size()));

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(NAF_DEFINITION,
                        NAF_DEFINITION.FIELD_NAF_CODE,
                        NAF_DEFINITION.FIELD_TITLE,
                        NAF_DEFINITION.FIELD_TITLE_65,
                        NAF_DEFINITION.FIELD_TITLE_40
                ).values((String) null, null, null, null)
                .onConflict(NAF_DEFINITION.FIELD_NAF_CODE)
                .doUpdate()
                .set(NAF_DEFINITION.FIELD_TITLE, asExcludedField(NAF_DEFINITION.FIELD_TITLE))
                .set(NAF_DEFINITION.FIELD_TITLE_65, asExcludedField(NAF_DEFINITION.FIELD_TITLE_65))
                .set(NAF_DEFINITION.FIELD_TITLE_40, asExcludedField(NAF_DEFINITION.FIELD_TITLE_40)));

        BatchWriterTools.recordsStreamOfBatch(batch)
                .map(Record::getPayload)
                .map(this::nafDefinitionBind)
                .forEach(consumer -> consumer.accept(batchInsertStatement));

        final var batchResult = batchInsertStatement.execute();
        if (batchResult.length > 0) {
            final int totalUpdated = Arrays.stream(batchResult).sum();
            LOGGER.info(String.format("%d rows affected", totalUpdated));
        } else {
            LOGGER.error("An error occurred while running batch");
        }
    }

    private Consumer<BatchBindStep> nafDefinitionBind(NafDefinition nafDefinition) {

        return batch -> batch.bind(
                nafDefinition.getNafCode(),
                nafDefinition.getTitle(),
                nafDefinition.getTitle65(),
                nafDefinition.getTitle40()
        );
    }

    private <T> Field<T> asExcludedField(@Nonnull Field<T> field) {
        return field(name("excluded", field.getName()), field.getType());
    }
}
