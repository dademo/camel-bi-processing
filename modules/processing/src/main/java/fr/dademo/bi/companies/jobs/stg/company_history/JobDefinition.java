package fr.dademo.bi.companies.jobs.stg.company_history;

import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jeasy.batch.core.mapper.RecordMapper;
import org.jeasy.batch.core.reader.RecordReader;
import org.jeasy.batch.core.writer.RecordWriter;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


@ApplicationScoped
@Named(JobDefinition.COMPANY_HISTORY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, CompanyHistory> {

    public static final String COMPANY_HISTORY_JOB_NAME = "stg_company_history";
    public static final String PERSISTENCE_UNIT_NAME = "stg";

    @Getter
    @ConfigProperty(name = "jobs.company-history.enabled", defaultValue = "false")
    boolean enabled = false;

    @Inject
    CompanyHistoryReader companyHistoryReader;

    @Inject
    CompanyHistoryMapper companyHistoryMapper;

    @Inject
    CompanyHistoryWriter companyHistoryWriter;

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_HISTORY_JOB_NAME;
    }

    @Override
    public int getBatchSize() {
        return 100000;
    }

    @Nonnull
    @Override
    public RecordReader<CSVRecord> getRecordReader() {
        return companyHistoryReader;
    }

    @Nonnull
    @Override
    public RecordMapper<CSVRecord, CompanyHistory> getRecordProcessor() {
        return companyHistoryMapper;
    }

    @Nonnull
    @Override
    public RecordWriter<CompanyHistory> getRecordWriter() {
        return companyHistoryWriter;
    }
}
