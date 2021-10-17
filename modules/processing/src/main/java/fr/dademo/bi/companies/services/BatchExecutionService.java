package fr.dademo.bi.companies.services;

import fr.dademo.bi.companies.repositories.entities.BatchExecutionHistoryEntity;
import org.jeasy.batch.core.job.JobReport;

public interface BatchExecutionService {

    BatchExecutionHistoryEntity saveExecution(JobReport jobReport);

    BatchExecutionHistoryEntity saveExecution(BatchExecutionHistoryEntity batchExecutionHistoryEntity);
}
