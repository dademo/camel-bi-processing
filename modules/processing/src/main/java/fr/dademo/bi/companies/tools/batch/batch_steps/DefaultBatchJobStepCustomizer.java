package fr.dademo.bi.companies.tools.batch.batch_steps;

import lombok.Getter;
import lombok.Setter;
import org.jberet.job.model.StepBuilder;

import javax.annotation.Nonnull;

@Getter
@Setter
public class DefaultBatchJobStepCustomizer implements BatchJobStepCustomizer {

    public static final int DEFAULT_START_LIMIT = 1;
    public static final int DEFAULT_TIME_LIMIT = -1;
    public static final int DEFAULT_RETRY_LIMIT = 0;
    public static final int DEFAULT_SKIP_LIMIT = 0;
    public static final int DEFAULT_CHUNK_SIZE = 100000;

    private int startLimit = DEFAULT_START_LIMIT;
    private int retryLimit = DEFAULT_TIME_LIMIT;
    private int timeLimit = DEFAULT_RETRY_LIMIT;
    private int itemCount = DEFAULT_CHUNK_SIZE;
    private int skipLimit = DEFAULT_SKIP_LIMIT;

    @Nonnull
    @Override
    public StepBuilder customizeStep(StepBuilder sb) {

        return sb
                .itemCount(getItemCount())
                .timeLimit(getTimeLimit())
                .retryLimit(getRetryLimit())
                .skipLimit(getSkipLimit())
                .startLimit(getStartLimit());
    }
}
