package fr.dademo.bi.companies.jobs.stg.company_legal_history;

import fr.dademo.bi.companies.configuration.JobsConfiguration;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;


@Component
@Qualifier(JobDefinition.COMPANY_LEGAL_HISTORY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, CompanyLegalHistory> {

    private static final String CONFIG_JOB_NAME = "company_legal_history";
    public static final String COMPANY_LEGAL_HISTORY_JOB_NAME = "stg_" + CONFIG_JOB_NAME;

    @Autowired
    private JobsConfiguration jobsConfiguration;

    @Autowired
    private CompanyLegalHistoryReader companyLegalHistoryReader;
    @Autowired
    private CompanyLegalHistoryMapper companyLegalHistoryMapper;
    @Autowired
    private CompanyLegalHistoryJdbcWriter companyLegalHistoryJdbcWriter;

    @Nonnull
    protected JobsConfiguration.JobConfiguration getJobConfiguration() {
        return jobsConfiguration.getJobConfigurationByName(CONFIG_JOB_NAME);
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
        return companyLegalHistoryJdbcWriter;
    }
}
