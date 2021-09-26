package fr.dademo.bi.companies.tools.batch.job;

import org.jeasy.batch.core.job.Job;

import javax.annotation.Nonnull;

public interface BatchJobProvider {

    boolean isEnabled();

    @Nonnull
    Job getJob();
}
