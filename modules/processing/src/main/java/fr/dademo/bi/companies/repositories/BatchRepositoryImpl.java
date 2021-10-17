package fr.dademo.bi.companies.repositories;

import fr.dademo.bi.companies.repositories.entities.BatchExecutionHistoryEntity;
import io.quarkus.hibernate.orm.PersistenceUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class BatchRepositoryImpl implements BatchRepository {

    private static final String PERSISTENCE_UNIT = "batch";

    @Inject
    @PersistenceUnit(PERSISTENCE_UNIT)
    EntityManager entityManager;

    @Transactional
    public BatchExecutionHistoryEntity merge(BatchExecutionHistoryEntity batchExecutionHistoryEntity) {
        return entityManager.merge(batchExecutionHistoryEntity);
    }
}
