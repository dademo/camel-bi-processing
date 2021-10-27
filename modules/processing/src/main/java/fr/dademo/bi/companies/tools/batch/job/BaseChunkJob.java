package fr.dademo.bi.companies.tools.batch.job;

import fr.dademo.bi.companies.configuration.JobsConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Nonnull;

public abstract class BaseChunkJob<I, O> implements BatchJobProvider {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Nonnull
    protected abstract JobsConfiguration.JobConfiguration getJobConfiguration();

    @Nonnull
    protected abstract String getJobName();

    @Nonnull
    protected abstract ItemReader<I> getItemReader();

    @Nonnull
    protected abstract ItemProcessor<I, O> getItemProcessor();

    @Nonnull
    protected abstract ItemWriter<O> getItemWriter();


    @Nonnull
    @Bean
    @Override
    public Job getJob() {

        final var threadPoolExecutor = new ThreadPoolTaskExecutor();
        threadPoolExecutor.setMaxPoolSize(getJobConfiguration().getMaxThreads());

        final var step = stepBuilderFactory
                .get(getJobName())
                .<I, O>chunk(getJobConfiguration().getChunkSize())
                .reader(getItemReader())
                .processor(getItemProcessor())
                .writer(getItemWriter())
                .taskExecutor(threadPoolExecutor)
                .build();

        return jobBuilderFactory
                .get(getJobName())
                .start(step)
                .build();
    }
}
