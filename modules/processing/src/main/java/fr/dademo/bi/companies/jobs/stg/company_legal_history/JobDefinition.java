package fr.dademo.bi.companies.jobs.stg.company_legal_history;

import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import fr.dademo.bi.companies.tools.batch.writer.DefaultRecordWriterProvider;
import fr.dademo.bi.companies.tools.batch.writer.RecordWriterProvider;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jeasy.batch.core.mapper.RecordMapper;
import org.jeasy.batch.core.reader.RecordReader;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


@ApplicationScoped
@Named(JobDefinition.COMPANY_LEGAL_HISTORY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, CompanyLegalHistory> {

    public static final String COMPANY_LEGAL_HISTORY_JOB_NAME = "stg_company_legal_history";

    @Getter
    @ConfigProperty(name = "jobs.company-legal-history.enabled", defaultValue = "false")
    boolean enabled = false;

    @Getter
    @ConfigProperty(name = "jobs.company-legal-history.batch-size", defaultValue = "100000")
    int batchSize = 100000;

    @Getter
    @ConfigProperty(name = "jobs.company-legal-history.writer-type", defaultValue = "NO_ACTION")
    String recordWriterTypeStr = "NO_ACTION";

    @Inject
    CompanyLegalHistoryReader companyLegalHistoryReader;

    @Inject
    CompanyLegalHistoryMapper companyLegalHistoryMapper;

    @Inject
    CompanyLegalHistoryJdbcWriter companyLegalHistoryJdbcWriter;

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_LEGAL_HISTORY_JOB_NAME;
    }

    @Nonnull
    @Override
    public RecordReader<CSVRecord> getRecordReader() {
        return companyLegalHistoryReader;
    }

    @Nonnull
    @Override
    public RecordMapper<CSVRecord, CompanyLegalHistory> getRecordProcessor() {
        return companyLegalHistoryMapper;
    }

    @Nonnull
    @Override
    protected RecordWriterProvider<CompanyLegalHistory> getRecordWriterProvider() {

        return DefaultRecordWriterProvider.<CompanyLegalHistory>builder()
                .jdbcRecordWriter(companyLegalHistoryJdbcWriter)
                .build();
    }
}
