/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job;

import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.tools.batch.job.listeners.StepThreadPoolListener;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

import static fr.dademo.batch.tools.batch.job.JobSharedValues.MAX_THREAD_POOL_QUEUE_SIZE_FACTOR;

@SuppressWarnings("java:S125")
public abstract class AbstractChunkedStepProvider<I, O> implements ChunkedStepProvider {

    @Nonnull
    private final StepBuilderFactory stepBuilderFactory;

    protected AbstractChunkedStepProvider(@Nonnull StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Nonnull
    protected abstract ItemReader<I> getItemReader();

    @Nonnull
    protected abstract ItemProcessor<I, O> getItemProcessor();

    @Nonnull
    protected abstract ItemWriter<O> getItemWriter();

    protected abstract List<StepExecutionListener> getStepExecutionListeners();


    public final @Nonnull Step getStep(@NotBlank String jobName,
                                       @Nonnull BatchConfiguration.JobConfiguration jobConfiguration,
                                       @Nonnull List<ChunkListener> chunkListeners) {

        final var threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        final int poolSize = Optional.ofNullable(jobConfiguration.getMaxThreads())
            .orElseGet(BatchConfiguration.JobConfiguration::getDefaultMaxThreads);

        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.setMaxPoolSize(poolSize);
        threadPoolTaskExecutor.setQueueCapacity(poolSize * MAX_THREAD_POOL_QUEUE_SIZE_FACTOR);

        threadPoolTaskExecutor.setPrestartAllCoreThreads(true);
        threadPoolTaskExecutor.setDaemon(false);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setThreadNamePrefix(String.format("bi-job-%s-", jobName));

        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        final var jobChunkedStep = stepBuilderFactory
            .get(jobName)
            .<I, O>chunk(
                Optional.ofNullable(jobConfiguration.getChunkSize())
                    .orElseGet(BatchConfiguration.JobConfiguration::getDefaultChunkSize))
            .reader(getItemReader())
            .processor(getItemProcessor())
            .writer(getItemWriter())
            .taskExecutor(threadPoolTaskExecutor)
            .throttleLimit(poolSize);   // Our thread pool size;

        // Adding a listener to start and stop the thread pool and avoid unused threads when finished the batch
        jobChunkedStep.listener(new StepThreadPoolListener(threadPoolTaskExecutor));
        getStepExecutionListeners().forEach(jobChunkedStep::listener);
        chunkListeners.forEach(jobChunkedStep::listener);

        return jobChunkedStep.build();
    }
}
