package fr.dademo.bi.companies.tools.batch.batch_steps;

import org.jberet.job.model.Step;

import javax.annotation.Nonnull;

public interface BatchJobStepProvider {

    boolean isEnabled();

    @Nonnull
    Step getJobStep();
}
