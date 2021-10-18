package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jeasy.batch.core.processor.RecordProcessor;
import org.jeasy.batch.core.reader.RecordReader;
import org.jeasy.batch.core.writer.RecordWriter;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


@ApplicationScoped
@Named(JobDefinition.COMPANY_INHERITANCE_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, CompanyInheritance> {

    public static final String COMPANY_INHERITANCE_JOB_NAME = "stg_companies_inheritance";

    @Getter
    @ConfigProperty(name = "jobs.company-inheritance.enabled", defaultValue = "false")
    boolean enabled = false;

    @Getter
    @ConfigProperty(name = "jobs.company-inheritance.batch-size", defaultValue = "100000")
    int batchSize = 100000;

    @Inject
    CompanyInheritanceReader companyInheritanceReader;

    @Inject
    CompanyInheritanceMapper companyInheritanceMapper;

    @Inject
    CompanyInheritanceWriter companyInheritanceWriter;

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_INHERITANCE_JOB_NAME;
    }

    @Nonnull
    @Override
    public RecordReader<CSVRecord> getRecordReader() {
        return companyInheritanceReader;
    }

    @Nonnull
    @Override
    public RecordProcessor<CSVRecord, CompanyInheritance> getRecordProcessor() {
        return companyInheritanceMapper;
    }

    @Nonnull
    @Override
    public RecordWriter<CompanyInheritance> getRecordWriter() {
        return companyInheritanceWriter;
    }
}
