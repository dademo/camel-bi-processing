package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.repositories.HttpDataQuerier;
import fr.dademo.bi.companies.tools.batch.batch_steps.BaseChunkJob;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


@ApplicationScoped
@Named(JobDefinition.NAF_JOB_NAME)
public class JobDefinition extends BaseChunkJob {

    public static final String NAF_JOB_NAME = "stg_naf";
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
    public String getJobName() {
        return NAF_JOB_NAME;
    }
}
