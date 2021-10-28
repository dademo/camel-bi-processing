package fr.dademo.bi.companies.jobs.stg.company_legal;

import fr.dademo.bi.companies.configuration.BatchConfiguration;
import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegal;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;


@Component(JobDefinition.COMPANY_LEGAL_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, CompanyLegal> {

    private static final String CONFIG_JOB_NAME = "company-legal";
    private static final String NORMALIZED_CONFIG_JOB_NAME = "company_legal";
    public static final String COMPANY_LEGAL_JOB_NAME = "stg_" + NORMALIZED_CONFIG_JOB_NAME;

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private CompanyLegalReader companyLegalReader;
    @Autowired
    private CompanyLegalMapper companyLegalMapper;
    @Autowired
    private CompanyLegalWriter companyLegalWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_LEGAL_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return companyLegalReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, CompanyLegal> getItemProcessor() {
        return companyLegalMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<CompanyLegal> getItemWriter() {

        return companyLegalWriter;
    }
}
