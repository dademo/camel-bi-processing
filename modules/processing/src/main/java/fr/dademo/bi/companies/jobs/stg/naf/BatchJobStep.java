package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.repositories.HttpDataQuerier;
import fr.dademo.bi.companies.tools.batch.batch_steps.BaseChunkBatchStep;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


@ApplicationScoped
@Named(BatchJobStep.NAF_JOB_STEP_NAME)
public class BatchJobStep extends BaseChunkBatchStep {

    public static final String NAF_JOB_STEP_NAME = "stg_naf";
    public static final String PERSISTENCE_UNIT_NAME = "stg";

    @Inject
    HttpDataQuerier httpDataQuerier;

    @Getter
    @ConfigProperty(name = "jobs.naf.enabled", defaultValue = "false")
    boolean enabled = false;

    @Nonnull
    @Override
    public String getItemReaderBeanName() {
        return NafReader.BEAN_NAME;
    }

    @Nonnull
    @Override
    public String getItemProcessorBeanName() {
        return NafProcessor.BEAN_NAME;
    }

    @Nonnull
    @Override
    public String getItemWriterBeanName() {
        return NafWriter.BEAN_NAME;
    }

    @Nonnull
    @Override
    public String getJobStepName() {
        return NAF_JOB_STEP_NAME;
    }
}
