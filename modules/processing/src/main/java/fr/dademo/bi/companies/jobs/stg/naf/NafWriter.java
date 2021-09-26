package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.jobs.stg.naf.entities.NafDefinition;
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

import static fr.dademo.bi.companies.jobs.stg.naf.JobDefinition.PERSISTENCE_UNIT_NAME;

@ApplicationScoped
public class NafWriter extends JpaRecordWriterSupport<NafDefinition> {

    private static final Logger LOGGER = Logger.getLogger(NafWriter.class);

    @Getter(AccessLevel.PROTECTED)
    @Inject
    @PersistenceUnit(PERSISTENCE_UNIT_NAME)
    EntityManagerFactory entityManagerFactory;

    @Override
    public void writeRecords(Batch<NafDefinition> batch) {

        LOGGER.info(String.format("Writing %d naf definition entities", batch.size()));
        BatchWriterTools.recordsStreamOfBatch(batch)
                .map(Record::getPayload)
                .forEach(this::saveNafDefinition);
        entityManagerFlush();
    }

    private void saveNafDefinition(NafDefinition nafDefinition) {
        getLocalEntityManager().merge(nafDefinition);
    }
}
