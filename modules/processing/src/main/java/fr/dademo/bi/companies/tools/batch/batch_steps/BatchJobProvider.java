package fr.dademo.bi.companies.tools.batch.batch_steps;

import org.jberet.job.model.Job;

import javax.annotation.Nonnull;

public interface BatchJobProvider {

    boolean isEnabled();

    @Nonnull
    Job getJob();
}
