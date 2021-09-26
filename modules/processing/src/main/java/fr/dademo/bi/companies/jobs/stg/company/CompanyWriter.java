package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.bi.companies.jobs.stg.company.entities.CompanyEntity;
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

import static fr.dademo.bi.companies.jobs.stg.company.JobDefinition.PERSISTENCE_UNIT_NAME;

@ApplicationScoped
public class CompanyWriter extends JpaRecordWriterSupport<CompanyEntity> {

    private static final Logger LOGGER = Logger.getLogger(CompanyWriter.class);

    @Inject
    @PersistenceUnit(PERSISTENCE_UNIT_NAME)
    @Getter(AccessLevel.PROTECTED)
    EntityManagerFactory entityManagerFactory;

    @Override
    public void writeRecords(Batch<CompanyEntity> batch) {

        LOGGER.info(String.format("Writing %d company history entities", batch.size()));
        BatchWriterTools.recordsStreamOfBatch(batch)
                .map(Record::getPayload)
                .forEach(this::saveCompany);
        entityManagerFlush();
    }

    private void saveCompany(CompanyEntity companyEntity) {
        getLocalEntityManager().merge(companyEntity);
    }
}
