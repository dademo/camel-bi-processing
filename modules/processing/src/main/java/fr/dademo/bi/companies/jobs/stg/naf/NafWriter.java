package fr.dademo.bi.companies.jobs.stg.naf;

import io.quarkus.hibernate.orm.PersistenceUnit;
import org.jboss.logging.Logger;

import javax.batch.api.chunk.ItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

import static fr.dademo.bi.companies.jobs.stg.companies_history.BatchJobStep.PERSISTENCE_UNIT_NAME;

@Dependent
@Named(NafWriter.BEAN_NAME)
public class NafWriter implements ItemWriter {

    public static final String BEAN_NAME = "NafWriter";
    private static final Logger LOGGER = Logger.getLogger(NafWriter.class);

    @Inject
    @PersistenceUnit(PERSISTENCE_UNIT_NAME)
    EntityManager entityManager;

    @Override
    public void open(Serializable checkpoint) {
    }

    @Override
    public void close() {
    }

    public void writeItems(List<Object> items) {
        LOGGER.info(String.format("Writing %d items", items.size()));
        items.forEach(entityManager::merge);
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
}
