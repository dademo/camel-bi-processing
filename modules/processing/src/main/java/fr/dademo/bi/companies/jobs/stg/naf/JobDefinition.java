package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import fr.dademo.bi.companies.tools.batch.writer.DefaultRecordWriterProvider;
import fr.dademo.bi.companies.tools.batch.writer.RecordWriterProvider;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jeasy.batch.core.processor.RecordProcessor;
import org.jeasy.batch.core.reader.RecordReader;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


@ApplicationScoped
@Named(JobDefinition.NAF_JOB_NAME)
public class JobDefinition extends BaseChunkJob<NafDefinitionContainer, NafDefinition> {

    public static final String NAF_JOB_NAME = "stg_naf";

    @Getter
    @ConfigProperty(name = "jobs.naf.enabled", defaultValue = "false")
    boolean enabled = false;

    @Getter
    @ConfigProperty(name = "jobs.naf.batch-size", defaultValue = "1000")
    int batchSize = 1000;

    @Getter
    @ConfigProperty(name = "jobs.naf.writer-type", defaultValue = "NO_ACTION")
    String recordWriterTypeStr = "NO_ACTION";

    @Inject
    NafReader nafReader;

    @Inject
    NafProcessor nafProcessor;

    @Inject
    NafJdbcWriter nafJdbcWriter;

    @Nonnull
    @Override
    public String getJobName() {
        return NAF_JOB_NAME;
    }

    @Nonnull
    @Override
    public RecordReader<NafDefinitionContainer> getRecordReader() {
        return nafReader;
    }

    @Nonnull
    @Override
    public RecordProcessor<NafDefinitionContainer, NafDefinition> getRecordProcessor() {
        return nafProcessor;
    }

    @Nonnull
    @Override
    protected RecordWriterProvider<NafDefinition> getRecordWriterProvider() {

        return DefaultRecordWriterProvider.<NafDefinition>builder()
                .jdbcRecordWriter(nafJdbcWriter)
                .build();
    }
}
