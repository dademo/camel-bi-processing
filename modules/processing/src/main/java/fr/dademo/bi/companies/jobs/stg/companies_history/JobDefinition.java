package fr.dademo.bi.companies.jobs.stg.companies_history;

import fr.dademo.bi.companies.tools.batch.batch_steps.BaseChunkJob;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


@ApplicationScoped
@Named(JobDefinition.COMPANIES_HISTORY_JOB_NAME)
public class JobDefinition extends BaseChunkJob {

    public static final String COMPANIES_HISTORY_JOB_NAME = "stg_companies_history";
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
    public String getJobName() {
        return COMPANIES_HISTORY_JOB_NAME;
    }
}
