/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * @author dademo
 */
@Getter(AccessLevel.PROTECTED)
public class SimpleChunkedStepProvider<I, O> extends AbstractChunkedStepProvider<I, O> {

    private final ItemReader<I> itemReader;
    private final ItemProcessor<I, O> itemProcessor;
    private final ItemWriter<O> itemWriter;
    private final List<StepExecutionListener> stepExecutionListeners;

    public SimpleChunkedStepProvider(@Nonnull JobRepository jobRepository,
                                     @Nonnull PlatformTransactionManager platformTransactionManager,
                                     @Nonnull ItemReader<I> itemReader,
                                     @Nonnull ItemProcessor<I, O> itemProcessor,
                                     @Nonnull ItemWriter<O> itemWriter,
                                     @Nonnull List<StepExecutionListener> stepExecutionListeners) {
        super(jobRepository, platformTransactionManager);
        this.itemReader = itemReader;
        this.itemProcessor = itemProcessor;
        this.itemWriter = itemWriter;
        this.stepExecutionListeners = stepExecutionListeners;
    }
}
