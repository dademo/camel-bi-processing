package fr.dademo.bi.companies.services;

import fr.dademo.bi.companies.repositories.BatchRepository;
import fr.dademo.bi.companies.repositories.entities.BatchExecutionCustomMetricEntity;
import fr.dademo.bi.companies.repositories.entities.BatchExecutionErrorEntity;
import fr.dademo.bi.companies.repositories.entities.BatchExecutionHistoryEntity;
import fr.dademo.bi.companies.repositories.entities.BatchExecutionPropertyEntity;
import org.jeasy.batch.core.job.JobReport;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@ApplicationScoped
public class BatchExecutionServiceImpl implements BatchExecutionService {

    @Inject
    BatchRepository batchRepository;

    public BatchExecutionHistoryEntity saveExecution(JobReport jobReport) {
        return batchRepository.merge(toJobExecutionHistoryEntity(jobReport));
    }

    public BatchExecutionHistoryEntity saveExecution(BatchExecutionHistoryEntity batchExecutionHistoryEntity) {
        return batchRepository.merge(batchExecutionHistoryEntity);
    }

    private BatchExecutionHistoryEntity toJobExecutionHistoryEntity(JobReport jobReport) {

        return BatchExecutionHistoryEntity.builder()
                .name(jobReport.getJobName())
                .status(BatchExecutionHistoryEntity.BatchStatus.of(jobReport.getStatus()))
                .start(jobReport.getMetrics().getStartTime())
                .end(jobReport.getMetrics().getEndTime())
                .readCount(jobReport.getMetrics().getReadCount())
                .writeCount(jobReport.getMetrics().getWriteCount())
                .filterCount(jobReport.getMetrics().getFilterCount())
                .errorCount(jobReport.getMetrics().getErrorCount())
                .batchExecutionCustomMetrics(toBatchExecutionCustomMetric(jobReport.getMetrics().getCustomMetrics()))
                .executionProperties(toBatchExecutionProperty(jobReport.getSystemProperties()))
                .executionError(Optional.ofNullable(jobReport.getLastError()).map(this::toBatchExecutionError).orElse(null))
                .build();
    }

    private List<BatchExecutionPropertyEntity> toBatchExecutionProperty(Properties jobProperties) {

        return jobProperties.entrySet().stream()
                .map(entry -> BatchExecutionPropertyEntity.builder()
                        .propertyName(Optional.ofNullable(entry.getKey())
                                .map(Object::toString)
                                .orElse(null))
                        .propertyValue(Optional.ofNullable(entry.getValue())
                                .map(Object::toString)
                                .orElse(null))
                        .build())
                .collect(Collectors.toList());
    }

    private List<BatchExecutionCustomMetricEntity> toBatchExecutionCustomMetric(Map<String, Object> customMetrics) {

        return customMetrics.entrySet().stream()
                .map(entry -> BatchExecutionCustomMetricEntity.builder()
                        .name(entry.getKey())
                        .value(Optional.ofNullable(entry.getValue())
                                .map(Object::toString)
                                .orElse(null))
                        .build())
                .collect(Collectors.toList());
    }

    private BatchExecutionErrorEntity toBatchExecutionError(Throwable batchExecutionError) {

        return BatchExecutionErrorEntity.builder()
                .name(batchExecutionError.getClass().getName())
                .error(batchExecutionError.getMessage())
                .localizedError(batchExecutionError.getLocalizedMessage())
                .stackTrace(formattedStacktrace(batchExecutionError))
                .build();
    }

    private String formattedStacktrace(Throwable throwable) {
        var sw = new StringWriter();
        var pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return pw.toString();
    }
}
