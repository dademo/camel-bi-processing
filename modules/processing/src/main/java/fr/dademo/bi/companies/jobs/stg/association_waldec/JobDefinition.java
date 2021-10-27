package fr.dademo.bi.companies.jobs.stg.association_waldec;

import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec;
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
@Named(JobDefinition.ASSOCIATION_WALDEC_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, AssociationWaldec> {

    public static final String ASSOCIATION_WALDEC_JOB_NAME = "stg_association_waldec";

    @Getter
    @ConfigProperty(name = "jobs.association-waldec.enabled", defaultValue = "false")
    boolean enabled = false;

    @Getter
    @ConfigProperty(name = "jobs.association-waldec.batch-size", defaultValue = "100000")
    int batchSize = 100000;

    @Getter
    @ConfigProperty(name = "jobs.association-waldec.writer-type", defaultValue = "NO_ACTION")
    String recordWriterTypeStr = "NO_ACTION";

    @Inject
    AssociationWaldecReader associationWaldecReader;

    @Inject
    AssociationWaldecMapper associationWaldecMapper;

    @Inject
    AssociationWaldecJdbcWriter associationWaldecJdbcWriter;

    @Nonnull
    @Override
    public String getJobName() {
        return ASSOCIATION_WALDEC_JOB_NAME;
    }

    @Nonnull
    @Override
    public RecordReader<CSVRecord> getRecordReader() {
        return associationWaldecReader;
    }

    @Nonnull
    @Override
    public RecordMapper<CSVRecord, AssociationWaldec> getRecordProcessor() {
        return associationWaldecMapper;
    }

    @Nonnull
    @Override
    protected RecordWriterProvider<AssociationWaldec> getRecordWriterProvider() {

        return DefaultRecordWriterProvider.<AssociationWaldec>builder()
                .jdbcRecordWriter(associationWaldecJdbcWriter)
                .build();
    }
}
