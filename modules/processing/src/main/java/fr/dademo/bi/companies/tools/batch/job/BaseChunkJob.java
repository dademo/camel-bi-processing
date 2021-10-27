package fr.dademo.bi.companies.tools.batch.job;

import fr.dademo.bi.companies.tools.batch.RecordWriterType;
import fr.dademo.bi.companies.tools.batch.writer.RecordWriterProvider;
import org.jeasy.batch.core.job.Job;
import org.jeasy.batch.core.job.JobBuilder;
import org.jeasy.batch.core.processor.RecordProcessor;
import org.jeasy.batch.core.reader.RecordReader;

import javax.annotation.Nonnull;
import java.util.function.Function;

public abstract class BaseChunkJob<I, O> implements BatchJobProvider {

    @Nonnull
    protected abstract RecordReader<I> getRecordReader();

    @Nonnull
    protected abstract RecordProcessor<I, O> getRecordProcessor();

    @Nonnull
    protected abstract RecordWriterProvider<O> getRecordWriterProvider();

    @Nonnull
    protected abstract String getRecordWriterTypeStr();

    @Nonnull
    protected RecordWriterType getRecordWriterType() {
        return RecordWriterType.valueOf(getRecordWriterTypeStr());
    }

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
                        .writer(getRecordWriterProvider().getRecordWriter(getRecordWriterType()))
                        .batchSize(getBatchSize())
                ).build();
    }
}
