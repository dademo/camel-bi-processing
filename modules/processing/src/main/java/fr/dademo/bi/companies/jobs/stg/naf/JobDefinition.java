package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jeasy.batch.core.processor.RecordProcessor;
import org.jeasy.batch.core.reader.RecordReader;
import org.jeasy.batch.core.writer.RecordWriter;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


@ApplicationScoped
@Named(JobDefinition.NAF_JOB_NAME)
public class JobDefinition extends BaseChunkJob<NafDefinitionContainer, NafDefinition> {

    public static final String NAF_JOB_NAME = "stg_naf";
    public static final String PERSISTENCE_UNIT_NAME = "stg";

    @Getter
    @ConfigProperty(name = "jobs.naf.enabled", defaultValue = "false")
    boolean enabled = false;

    @Inject
    NafReader nafReader;

    @Inject
    NafProcessor nafProcessor;

    @Inject
    NafWriter nafWriter;

    @Nonnull
    @Override
    public String getJobName() {
        return NAF_JOB_NAME;
    }

    @Override
    public int getBatchSize() {
        return 1000;
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
    public RecordWriter<NafDefinition> getRecordWriter() {
        return nafWriter;
    }
}
