package fr.dademo.bi.companies.tools.batch.job;

import fr.dademo.bi.companies.configuration.BatchConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class BaseChunkJob<I, O> implements BatchJobProvider {

    public static final int MAX_THREAD_POOL_QUEUE_SIZE_FACTOR = 5;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BatchConfiguration batchConfiguration;


    @Nonnull
    protected abstract BatchConfiguration.JobConfiguration getJobConfiguration();

    @Nonnull
    protected abstract String getJobName();

    @Nonnull
    protected abstract ItemReader<I> getItemReader();

    @Nonnull
    protected abstract ItemProcessor<I, O> getItemProcessor();

    @Nonnull
    protected abstract ItemWriter<O> getItemWriter();


    @Override
    public Job getJob() {

        if (Boolean.TRUE.equals(
                Optional.ofNullable(getJobConfiguration().getEnabled())
                        .orElseGet(BatchConfiguration.JobConfiguration::getDefaultIsEnabled))) {

            final var jobName = getJobName();
            final var threadPoolExecutor = new ThreadPoolTaskExecutor();
            final int poolSize = Optional.ofNullable(getJobConfiguration().getMaxThreads())
                    .orElseGet(BatchConfiguration.JobConfiguration::getDefaultMaxThreads);

            threadPoolExecutor.setCorePoolSize(poolSize);
            threadPoolExecutor.setMaxPoolSize(poolSize);
            threadPoolExecutor.setQueueCapacity(poolSize * MAX_THREAD_POOL_QUEUE_SIZE_FACTOR);
            threadPoolExecutor.setDaemon(false);
            threadPoolExecutor.setThreadNamePrefix(jobName);

            threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            threadPoolExecutor.initialize();

            final var step = stepBuilderFactory
                    .get(jobName)
                    .<I, O>chunk(
                            Optional.ofNullable(getJobConfiguration().getChunkSize())
                                    .orElseGet(BatchConfiguration.JobConfiguration::getDefaultChunkSize))
                    .reader(getItemReader())
                    .processor(getItemProcessor())
                    .writer(getItemWriter())
                    .taskExecutor(threadPoolExecutor)
                    .build();

            return jobBuilderFactory
                    .get(jobName)
                    .preventRestart()
                    .start(step)
                    .build();
        } else {
            return null;
        }
    }
}
