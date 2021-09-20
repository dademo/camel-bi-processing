package fr.dademo.bi.companies;

import fr.dademo.bi.companies.tools.batch.batch_steps.BatchJobProvider;
import fr.dademo.bi.companies.tools.batch.job_configuration.OrderedJobsProvider;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.dademo.bi.companies.jobs.stg.companies_history.JobDefinition.COMPANIES_HISTORY_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.naf.JobDefinition.NAF_JOB_NAME;

@ApplicationScoped
public class AppJobsProvider implements OrderedJobsProvider {

    @Inject
    @Named(NAF_JOB_NAME)
    BatchJobProvider nafJobSteps;

    @Inject
    @Named(COMPANIES_HISTORY_JOB_NAME)
    BatchJobProvider companiesHistoryJobSteps;


    @Nonnull
    @Override
    public List<BatchJobProvider> getJobProviderss() {
        return Stream.of(
                        nafJobSteps,
                        companiesHistoryJobSteps
                )
                .filter(BatchJobProvider::isEnabled)
                .collect(Collectors.toList());
    }
}
