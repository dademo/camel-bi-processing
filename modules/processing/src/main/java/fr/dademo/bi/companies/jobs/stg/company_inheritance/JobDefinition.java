package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.bi.companies.configuration.JobsConfiguration;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
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
@Qualifier(JobDefinition.COMPANY_INHERITANCE_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, CompanyInheritance> {

    private static final String CONFIG_JOB_NAME = "companies_inheritance";
    public static final String COMPANY_INHERITANCE_JOB_NAME = "stg_" + CONFIG_JOB_NAME;


    @Autowired
    private JobsConfiguration jobsConfiguration;

    @Autowired
    private CompanyInheritanceReader companyInheritanceReader;
    @Autowired
    private CompanyInheritanceMapper companyInheritanceMapper;
    @Autowired
    private CompanyInheritanceJdbcWriter companyInheritanceJdbcWriter;

    @Nonnull
    protected JobsConfiguration.JobConfiguration getJobConfiguration() {
        return jobsConfiguration.getJobConfigurationByName(CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_INHERITANCE_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return companyInheritanceReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, CompanyInheritance> getItemProcessor() {
        return companyInheritanceMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<CompanyInheritance> getItemWriter() {
        return companyInheritanceJdbcWriter;
    }
}
