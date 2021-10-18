package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
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
@Named(JobDefinition.COMPANY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, Company> {

    public static final String COMPANY_JOB_NAME = "stg_company";

    @Getter
    @ConfigProperty(name = "jobs.company.enabled", defaultValue = "false")
    boolean enabled = false;

    @Getter
    @ConfigProperty(name = "jobs.company.batch-size", defaultValue = "100000")
    int batchSize = 100000;

    @Inject
    CompanyReader companyReader;

    @Inject
    CompanyMapper companyMapper;

    @Inject
    CompanyWriter companyWriter;

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_JOB_NAME;
    }

    @Nonnull
    @Override
    public RecordReader<CSVRecord> getRecordReader() {
        return companyReader;
    }

    @Nonnull
    @Override
    public RecordMapper<CSVRecord, Company> getRecordProcessor() {
        return companyMapper;
    }

    @Nonnull
    @Override
    public RecordWriter<Company> getRecordWriter() {
        return companyWriter;
    }
}
