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

    public static final String COMPANY_LEGAL_CONFIG_JOB_NAME = "company-legal";
    public static final String COMPANY_LEGAL_NORMALIZED_CONFIG_JOB_NAME = "company_legal";
    public static final String COMPANY_LEGAL_JOB_NAME = "stg_" + COMPANY_LEGAL_NORMALIZED_CONFIG_JOB_NAME;

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private CompanyLegalItemReader companyLegalItemReader;
    @Autowired
    private CompanyLegalItemMapper companyLegalItemMapper;
    @Autowired
    private CompanyLegalItemWriter companyLegalItemWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(COMPANY_LEGAL_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_LEGAL_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return companyLegalItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, CompanyLegal> getItemProcessor() {
        return companyLegalItemMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<CompanyLegal> getItemWriter() {

        return companyLegalItemWriter;
    }
}
