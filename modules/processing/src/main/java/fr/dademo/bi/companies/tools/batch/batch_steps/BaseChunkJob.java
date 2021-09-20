package fr.dademo.bi.companies.tools.batch.batch_steps;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
import org.jberet.job.model.Step;
import org.jberet.job.model.StepBuilder;

import javax.annotation.Nonnull;

@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseChunkJob implements BatchJobProvider {

    @Nonnull
    BatchJobStepCustomizer batchJobStepCustomizer = new DefaultBatchJobStepCustomizer();

    @Nonnull
    public abstract String getItemReaderBeanName();

    @Nonnull
    public abstract String getItemProcessorBeanName();

    @Nonnull
    public abstract String getItemWriterBeanName();

    @Nonnull
    public abstract String getJobName();

    @Nonnull
    public String getJobTaskName() {
        return String.format("%s-task", getJobName());
    }

    @Nonnull
    @Override
    public Job getJob() {

        return new JobBuilder(getJobName())
                .step(getDefaultStep())
                .build();
    }

    protected Step getDefaultStep() {

        return batchJobStepCustomizer.customizeStep(
                        new StepBuilder(getJobTaskName())
                                //.reader(getItemReaderClass().getName())
                                .reader(getItemReaderBeanName())
                                .processor(getItemProcessorBeanName())
                                .writer(getItemWriterBeanName())
                                .checkpointPolicy("item"))
                .build();
    }
}
