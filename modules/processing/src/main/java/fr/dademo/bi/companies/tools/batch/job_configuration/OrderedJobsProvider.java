package fr.dademo.bi.companies.tools.batch.job_configuration;

import fr.dademo.bi.companies.tools.batch.job.BatchJobProvider;

import javax.annotation.Nonnull;
import java.util.List;

public interface OrderedJobsProvider {

    @Nonnull
    List<BatchJobProvider> getJobProviders();
}
