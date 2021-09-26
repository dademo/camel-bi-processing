package fr.dademo.bi.companies.jobs.stg.company_history;

import fr.dademo.bi.companies.jobs.stg.company_history.entities.CompanyHistoryEntity;
import fr.dademo.bi.companies.tools.batch.JpaRecordWriterSupport;
import fr.dademo.bi.companies.tools.batch.writer.BatchWriterTools;
import io.quarkus.hibernate.orm.PersistenceUnit;
import lombok.AccessLevel;
import lombok.Getter;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.record.Batch;
import org.jeasy.batch.core.record.Record;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import static fr.dademo.bi.companies.jobs.stg.company_history.JobDefinition.PERSISTENCE_UNIT_NAME;

@ApplicationScoped
public class CompanyHistoryWriter extends JpaRecordWriterSupport<CompanyHistoryEntity> {

    private static final Logger LOGGER = Logger.getLogger(CompanyHistoryWriter.class);

    @Inject
    @PersistenceUnit(PERSISTENCE_UNIT_NAME)
    @Getter(AccessLevel.PROTECTED)
    EntityManagerFactory entityManagerFactory;

    @Override
    public void writeRecords(Batch<CompanyHistoryEntity> batch) {

        LOGGER.info(String.format("Writing %d company history entities", batch.size()));
        BatchWriterTools.recordsStreamOfBatch(batch)
                .map(Record::getPayload)
                .forEach(this::saveCompanyHistory);
        entityManagerFlush();
    }

    private void saveCompanyHistory(CompanyHistoryEntity companyHistoryEntity) {
        getLocalEntityManager().merge(companyHistoryEntity);
    }
}
