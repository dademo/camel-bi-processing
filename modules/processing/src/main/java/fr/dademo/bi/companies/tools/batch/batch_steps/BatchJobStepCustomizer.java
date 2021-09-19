package fr.dademo.bi.companies.tools.batch.batch_steps;

import org.jberet.job.model.StepBuilder;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface BatchJobStepCustomizer {

    @Nonnull
    StepBuilder customizeStep(StepBuilder sb);
}
