package fr.dademo.bi.companies.jobs.stg.company_legal;

import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegal;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import fr.dademo.bi.companies.tools.batch.writer.DefaultRecordWriterProvider;
import fr.dademo.bi.companies.tools.batch.writer.RecordWriterProvider;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jeasy.batch.core.mapper.RecordMapper;
import org.jeasy.batch.core.reader.RecordReader;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


@ApplicationScoped
@Named(JobDefinition.COMPANY_LEGAL_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, CompanyLegal> {

    public static final String COMPANY_LEGAL_JOB_NAME = "stg_company_legal";

    @Getter
    @ConfigProperty(name = "jobs.company-legal.enabled", defaultValue = "false")
    boolean enabled = false;

    @Getter
    @ConfigProperty(name = "jobs.company-legal.batch-size", defaultValue = "100000")
    int batchSize = 100000;

    @Getter
    @ConfigProperty(name = "jobs.company-legal.writer-type", defaultValue = "NO_ACTION")
    String recordWriterTypeStr = "NO_ACTION";

    @Inject
    CompanyLegalReader companyLegalReader;

    @Inject
    CompanyLegalMapper companyLegalMapper;

    @Inject
    CompanyLegalJdbcWriter companyLegalJdbcWriter;

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_LEGAL_JOB_NAME;
    }

    @Nonnull
    @Override
    public RecordReader<CSVRecord> getRecordReader() {
        return companyLegalReader;
    }

    @Nonnull
    @Override
    public RecordMapper<CSVRecord, CompanyLegal> getRecordProcessor() {
        return companyLegalMapper;
    }

    @Nonnull
    @Override
    protected RecordWriterProvider<CompanyLegal> getRecordWriterProvider() {

        return DefaultRecordWriterProvider.<CompanyLegal>builder()
                .jdbcRecordWriter(companyLegalJdbcWriter)
                .build();
    }
}
