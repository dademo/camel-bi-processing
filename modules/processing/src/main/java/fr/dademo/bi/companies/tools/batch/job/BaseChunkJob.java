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

public abstract class BaseChunkJob<I, O> implements BatchJobProvider {


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

            final var threadPoolExecutor = new ThreadPoolTaskExecutor();
            threadPoolExecutor.setMaxPoolSize(
                    Optional.ofNullable(getJobConfiguration().getMaxThreads())
                            .orElseGet(BatchConfiguration.JobConfiguration::getDefaultMaxThreads)
            );
            threadPoolExecutor.initialize();

            final var step = stepBuilderFactory
                    .get(getJobName())
                    .<I, O>chunk(
                            Optional.ofNullable(getJobConfiguration().getChunkSize())
                                    .orElseGet(BatchConfiguration.JobConfiguration::getDefaultChunkSize))
                    .reader(getItemReader())
                    .processor(getItemProcessor())
                    .writer(getItemWriter())
                    .taskExecutor(threadPoolExecutor)
                    .build();

            return jobBuilderFactory
                    .get(getJobName())
                    .start(step)
                    .build();
        } else {
            return null;
        }
    }
}
