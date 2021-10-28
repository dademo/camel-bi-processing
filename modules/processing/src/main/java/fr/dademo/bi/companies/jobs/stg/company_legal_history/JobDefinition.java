package fr.dademo.bi.companies.jobs.stg.company_legal_history;

import fr.dademo.bi.companies.configuration.BatchConfiguration;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;


@Component(JobDefinition.COMPANY_LEGAL_HISTORY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, CompanyLegalHistory> {

    private static final String CONFIG_JOB_NAME = "company-legal-history";
    private static final String NORMALIZED_CONFIG_JOB_NAME = "company_legal_history";
    public static final String COMPANY_LEGAL_HISTORY_JOB_NAME = "stg_" + NORMALIZED_CONFIG_JOB_NAME;

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private CompanyLegalHistoryReader companyLegalHistoryReader;
    @Autowired
    private CompanyLegalHistoryMapper companyLegalHistoryMapper;
    @Autowired
    private CompanyLegalHistoryWriter companyLegalHistoryWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_LEGAL_HISTORY_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return companyLegalHistoryReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, CompanyLegalHistory> getItemProcessor() {
        return companyLegalHistoryMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<CompanyLegalHistory> getItemWriter() {
        return companyLegalHistoryWriter;
    }
}
