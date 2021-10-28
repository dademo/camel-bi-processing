package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.bi.companies.configuration.BatchConfiguration;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;


@Component(JobDefinition.COMPANY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, Company> {

    private static final String CONFIG_JOB_NAME = "company";
    private static final String NORMALIZED_CONFIG_JOB_NAME = "company";
    public static final String COMPANY_JOB_NAME = "stg_" + NORMALIZED_CONFIG_JOB_NAME;

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private CompanyReader companyReader;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private CompanyItemWriter companyItemWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return companyReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, Company> getItemProcessor() {
        return companyMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<Company> getItemWriter() {
        return companyItemWriter;
    }
}
