package fr.dademo.bi.companies.repositories;

import fr.dademo.bi.companies.repositories.entities.BatchExecutionHistoryEntity;

public interface BatchRepository {

    BatchExecutionHistoryEntity merge(BatchExecutionHistoryEntity batchExecutionHistoryEntity);
}
