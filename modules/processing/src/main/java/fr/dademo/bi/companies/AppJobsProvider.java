package fr.dademo.bi.companies;

import fr.dademo.bi.companies.tools.batch.job.BatchJobProvider;
import fr.dademo.bi.companies.tools.batch.job_configuration.OrderedJobsProvider;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.dademo.bi.companies.jobs.stg.company.JobDefinition.COMPANY_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_history.JobDefinition.COMPANY_HISTORY_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.JobDefinition.COMPANY_INHERITANCE_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.naf.JobDefinition.NAF_JOB_NAME;

@ApplicationScoped
public class AppJobsProvider implements OrderedJobsProvider {

    @Inject
    @Named(NAF_JOB_NAME)
    BatchJobProvider nafJobSteps;

    @Inject
    @Named(COMPANY_JOB_NAME)
    BatchJobProvider companyJobSteps;

    @Inject
    @Named(COMPANY_HISTORY_JOB_NAME)
    BatchJobProvider companyHistoryJobSteps;

    @Inject
    @Named(COMPANY_INHERITANCE_JOB_NAME)
    BatchJobProvider companyInheritanceJobSteps;


    @Nonnull
    @Override
    public List<BatchJobProvider> getJobProviders() {
        return Stream.of(
                        nafJobSteps,
                        companyJobSteps,
                        companyHistoryJobSteps,
                        companyInheritanceJobSteps
                )
                .filter(BatchJobProvider::isEnabled)
                .collect(Collectors.toUnmodifiableList());
    }
}
