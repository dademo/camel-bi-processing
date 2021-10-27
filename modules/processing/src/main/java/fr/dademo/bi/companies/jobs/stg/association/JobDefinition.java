package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
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
@Named(JobDefinition.ASSOCIATION_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, Association> {

    public static final String ASSOCIATION_JOB_NAME = "stg_association";

    @Getter
    @ConfigProperty(name = "jobs.association.enabled", defaultValue = "false")
    boolean enabled = false;

    @Getter
    @ConfigProperty(name = "jobs.association.batch-size", defaultValue = "100000")
    int batchSize = 100000;

    @Getter
    @ConfigProperty(name = "jobs.association.writer-type", defaultValue = "NO_ACTION")
    String recordWriterTypeStr = "NO_ACTION";

    @Inject
    AssociationReader associationReader;

    @Inject
    AssociationMapper associationMapper;

    @Inject
    AssociationJdbcWriter associationJdbcWriter;

    @Nonnull
    @Override
    public String getJobName() {
        return ASSOCIATION_JOB_NAME;
    }

    @Nonnull
    @Override
    public RecordReader<CSVRecord> getRecordReader() {
        return associationReader;
    }

    @Nonnull
    @Override
    public RecordMapper<CSVRecord, Association> getRecordProcessor() {
        return associationMapper;
    }

    @Nonnull
    @Override
    protected RecordWriterProvider<Association> getRecordWriterProvider() {

        return DefaultRecordWriterProvider.<Association>builder()
                .jdbcRecordWriter(associationJdbcWriter)
                .build();
    }
}
