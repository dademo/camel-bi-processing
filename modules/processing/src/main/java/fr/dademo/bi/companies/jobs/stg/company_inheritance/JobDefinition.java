package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import fr.dademo.bi.companies.tools.batch.writer.DefaultRecordWriterProvider;
import fr.dademo.bi.companies.tools.batch.writer.RecordWriterProvider;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jeasy.batch.core.processor.RecordProcessor;
import org.jeasy.batch.core.reader.RecordReader;

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

    @Getter
    @ConfigProperty(name = "jobs.company-inheritance.writer-type", defaultValue = "NO_ACTION")
    String recordWriterTypeStr = "NO_ACTION";

    @Inject
    CompanyInheritanceReader companyInheritanceReader;

    @Inject
    CompanyInheritanceMapper companyInheritanceMapper;

    @Inject
    CompanyInheritanceJdbcWriter companyInheritanceJdbcWriter;

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
    protected RecordWriterProvider<CompanyInheritance> getRecordWriterProvider() {

        return DefaultRecordWriterProvider.<CompanyInheritance>builder()
                .jdbcRecordWriter(companyInheritanceJdbcWriter)
                .build();
    }
}
