package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import fr.dademo.bi.companies.tools.batch.writer.BatchWriterTools;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.record.Batch;
import org.jeasy.batch.core.record.Record;
import org.jeasy.batch.core.writer.RecordWriter;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.function.Consumer;

import static fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritanceTable.COMPANY_INHERITANCE;
import static fr.dademo.bi.companies.tools.DefaultAppBeans.STG_DSL_CONTEXT;

@ApplicationScoped
public class CompanyInheritanceWriter implements RecordWriter<CompanyInheritance> {

    private static final Logger LOGGER = Logger.getLogger(CompanyInheritanceWriter.class);

    @Inject
    @Named(STG_DSL_CONTEXT)
    DSLContext dslContext;

    @Override
    public void writeRecords(Batch<CompanyInheritance> batch) {

        LOGGER.info(String.format("Writing %d naf definition documents", batch.size()));

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(COMPANY_INHERITANCE,
                COMPANY_INHERITANCE.FIELD_COMPANY_PREDECESSOR_SIREN,
                COMPANY_INHERITANCE.FIELD_COMPANY_SUCCESSOR_SIREN,
                COMPANY_INHERITANCE.FIELD_COMPANY_SUCCESSION_DATE,
                COMPANY_INHERITANCE.FIELD_COMPANY_HEADQUARTER_CHANGE,
                COMPANY_INHERITANCE.FIELD_COMPANY_ECONOMICAL_CONTINUITY,
                COMPANY_INHERITANCE.FIELD_COMPANY_PROCESSING_DATE
        ).values((String) null, null, null, null, null, null));

        BatchWriterTools.recordsStreamOfBatch(batch)
                .map(Record::getPayload)
                .map(this::companyInheritanceBind)
                .forEach(consumer -> consumer.accept(batchInsertStatement));

        final var batchResult = batchInsertStatement.execute();
        if (batchResult.length > 0) {
            final var totalUpdated = Arrays.stream(batchResult).sum();
            LOGGER.info(String.format("%d rows affected", totalUpdated));
        } else {
            LOGGER.error("An error occurred while running batch");
        }
    }

    private Consumer<BatchBindStep> companyInheritanceBind(CompanyInheritance companyInheritance) {

        return batch -> batch.bind(
                companyInheritance.getCompanyPredecessorSiren(),
                companyInheritance.getCompanySuccessorSiren(),
                companyInheritance.getCompanySuccessionDate(),
                companyInheritance.getCompanyHeaderChanged(),
                companyInheritance.getCompanyEconomicalContinuity(),
                companyInheritance.getCompanyProcessingTimestamp()
        );
    }
}
