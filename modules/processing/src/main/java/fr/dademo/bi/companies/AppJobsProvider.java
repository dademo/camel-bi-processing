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

import static fr.dademo.bi.companies.jobs.stg.association.JobDefinition.ASSOCIATION_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.association_waldec.JobDefinition.ASSOCIATION_WALDEC_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company.JobDefinition.COMPANY_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_history.JobDefinition.COMPANY_HISTORY_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.JobDefinition.COMPANY_INHERITANCE_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_legal.JobDefinition.COMPANY_LEGAL_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_legal_history.JobDefinition.COMPANY_LEGAL_HISTORY_JOB_NAME;
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

    @Inject
    @Named(COMPANY_LEGAL_JOB_NAME)
    BatchJobProvider companyLegalJobSteps;

    @Inject
    @Named(COMPANY_LEGAL_HISTORY_JOB_NAME)
    BatchJobProvider companyLegalHistoryJobSteps;

    @Inject
    @Named(ASSOCIATION_JOB_NAME)
    BatchJobProvider associationJobSteps;

    @Inject
    @Named(ASSOCIATION_WALDEC_JOB_NAME)
    BatchJobProvider associationWaldecJobSteps;


    @Nonnull
    @Override
    public List<BatchJobProvider> getJobProviders() {
        return Stream.of(
                        nafJobSteps,
                        companyJobSteps,
                        companyHistoryJobSteps,
                        companyInheritanceJobSteps,
                        companyLegalJobSteps,
                        companyLegalHistoryJobSteps,
                        associationJobSteps,
                        associationWaldecJobSteps
                )
                .filter(BatchJobProvider::isEnabled)
                .collect(Collectors.toUnmodifiableList());
    }
}
