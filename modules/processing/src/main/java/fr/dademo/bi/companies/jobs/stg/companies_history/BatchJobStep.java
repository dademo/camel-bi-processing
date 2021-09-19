package fr.dademo.bi.companies.jobs.stg.companies_history;

import fr.dademo.bi.companies.tools.batch.batch_steps.BaseChunkBatchStep;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


@ApplicationScoped
@Named(BatchJobStep.COMPANIES_HISTORY_JOB_STEP_NAME)
public class BatchJobStep extends BaseChunkBatchStep {

    public static final String COMPANIES_HISTORY_JOB_STEP_NAME = "stg_companies_history";
    public static final String PERSISTENCE_UNIT_NAME = "stg";

    @Getter
    @ConfigProperty(name = "jobs.companies-history.enabled", defaultValue = "false")
    boolean enabled = false;

    @Nonnull
    @Override
    public String getItemReaderBeanName() {
        return CompaniesHistoryReader.BEAN_NAME;
    }

    @Nonnull
    @Override
    public String getItemProcessorBeanName() {
        return CompaniesHistoryProcessor.BEAN_NAME;
    }

    @Nonnull
    @Override
    public String getItemWriterBeanName() {
        return CompaniesHistoryWriter.BEAN_NAME;
    }

    @Nonnull
    @Override
    public String getJobStepName() {
        return COMPANIES_HISTORY_JOB_STEP_NAME;
    }
}
