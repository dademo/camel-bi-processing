package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.entities.CompanyInheritanceEntity;
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

import static fr.dademo.bi.companies.jobs.stg.company_inheritance.JobDefinition.PERSISTENCE_UNIT_NAME;

@ApplicationScoped
public class CompanyInheritanceWriter extends JpaRecordWriterSupport<CompanyInheritanceEntity> {

    private static final Logger LOGGER = Logger.getLogger(CompanyInheritanceWriter.class);

    @Getter(AccessLevel.PROTECTED)
    @Inject
    @PersistenceUnit(PERSISTENCE_UNIT_NAME)
    EntityManagerFactory entityManagerFactory;

    @Override
    public void writeRecords(Batch<CompanyInheritanceEntity> batch) {

        LOGGER.info(String.format("Writing %d company inheritance entities", batch.size()));
        BatchWriterTools.recordsStreamOfBatch(batch)
                .map(Record::getPayload)
                .forEach(this::saveCompanyInheritance);
        entityManagerFlush();
    }

    private void saveCompanyInheritance(CompanyInheritanceEntity companyInheritanceEntity) {
        getLocalEntityManager().merge(companyInheritanceEntity);
    }
}
