package fr.dademo.bi.companies.tools.batch.batch_steps;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jberet.job.model.Step;
import org.jberet.job.model.StepBuilder;

import javax.annotation.Nonnull;

@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseChunkBatchStep implements BatchJobStepProvider {

    @Nonnull
    BatchJobStepCustomizer batchJobStepCustomizer = new DefaultBatchJobStepCustomizer();

    @Nonnull
    public abstract String getItemReaderBeanName();

    @Nonnull
    public abstract String getItemProcessorBeanName();

    @Nonnull
    public abstract String getItemWriterBeanName();

    @Nonnull
    public abstract String getJobStepName();

    @Nonnull
    @Override
    public Step getJobStep() {

        return batchJobStepCustomizer.customizeStep(
                        new StepBuilder(getJobStepName())
                                //.reader(getItemReaderClass().getName())
                                .reader(getItemReaderBeanName())
                                .processor(getItemProcessorBeanName())
                                .writer(getItemWriterBeanName())
                                .checkpointPolicy("item"))
                .build();
    }

}
