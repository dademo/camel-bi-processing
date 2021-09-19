package fr.dademo.bi.companies;

import fr.dademo.bi.companies.tools.batch.batch_steps.BatchJobStepProvider;
import fr.dademo.bi.companies.tools.batch.job_configuration.OrderedJobStepsProvider;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.dademo.bi.companies.jobs.stg.companies_history.BatchJobStep.COMPANIES_HISTORY_JOB_STEP_NAME;
import static fr.dademo.bi.companies.jobs.stg.naf.BatchJobStep.NAF_JOB_STEP_NAME;

@ApplicationScoped
public class ApplicationJobStepsProvider implements OrderedJobStepsProvider {

    @Inject
    @Named(NAF_JOB_STEP_NAME)
    BatchJobStepProvider nafJobSteps;

    @Inject
    @Named(COMPANIES_HISTORY_JOB_STEP_NAME)
    BatchJobStepProvider companiesHistoryJobSteps;


    @Nonnull
    @Override
    public List<BatchJobStepProvider> getAppJobStep() {
        return Stream.of(
                        nafJobSteps,
                        companiesHistoryJobSteps
                )
                .filter(BatchJobStepProvider::isEnabled)
                .collect(Collectors.toList());
    }
}
