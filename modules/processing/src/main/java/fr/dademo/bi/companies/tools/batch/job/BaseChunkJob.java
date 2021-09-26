package fr.dademo.bi.companies.tools.batch.job;

import org.jeasy.batch.core.job.Job;
import org.jeasy.batch.core.job.JobBuilder;
import org.jeasy.batch.core.processor.RecordProcessor;
import org.jeasy.batch.core.reader.RecordReader;
import org.jeasy.batch.core.writer.RecordWriter;

import javax.annotation.Nonnull;
import java.util.function.Function;

public abstract class BaseChunkJob<I, O> implements BatchJobProvider {

    @Nonnull
    protected abstract RecordReader<I> getRecordReader();

    @Nonnull
    protected abstract RecordProcessor<I, O> getRecordProcessor();

    @Nonnull
    protected abstract RecordWriter<O> getRecordWriter();

    @Nonnull
    protected abstract String getJobName();

    protected abstract int getBatchSize();

    @Nonnull
    protected Function<JobBuilder<I, O>, JobBuilder<I, O>> getJobBuilderCustomizer() {
        return jobBuilder -> jobBuilder;
    }

    @Nonnull
    @Override
    public Job getJob() {

        return getJobBuilderCustomizer()
                .apply(new JobBuilder<I, O>()
                        .named(getJobName())
                        .reader(getRecordReader())
                        .processor(getRecordProcessor())
                        .writer(getRecordWriter())
                        .batchSize(getBatchSize())
                ).build();
    }
}
