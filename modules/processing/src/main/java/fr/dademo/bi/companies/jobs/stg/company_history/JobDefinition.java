package fr.dademo.bi.companies.jobs.stg.company_history;

import fr.dademo.bi.companies.configuration.BatchConfiguration;
import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;


@Component(JobDefinition.COMPANY_HISTORY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, CompanyHistory> {

    private static final String CONFIG_JOB_NAME = "company-history";
    private static final String NORMALIZED_CONFIG_JOB_NAME = "company_history";
    public static final String COMPANY_HISTORY_JOB_NAME = "stg_" + NORMALIZED_CONFIG_JOB_NAME;

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private CompanyHistoryReader companyHistoryReader;
    @Autowired
    private CompanyHistoryMapper companyHistoryMapper;
    @Autowired
    private CompanyHistoryItemWriter companyHistoryItemWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_HISTORY_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return companyHistoryReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, CompanyHistory> getItemProcessor() {
        return companyHistoryMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<CompanyHistory> getItemWriter() {
        return companyHistoryItemWriter;
    }
}
