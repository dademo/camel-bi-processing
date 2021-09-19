package fr.dademo.bi.companies.tools.batch.job_configuration;

import fr.dademo.bi.companies.tools.batch.batch_steps.BatchJobStepProvider;

import javax.annotation.Nonnull;
import java.util.List;

public interface OrderedJobStepsProvider {

    @Nonnull
    List<BatchJobStepProvider> getAppJobStep();
}
