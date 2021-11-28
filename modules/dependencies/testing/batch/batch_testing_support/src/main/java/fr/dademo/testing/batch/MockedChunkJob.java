/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.testing.batch;

import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import lombok.Builder;
import lombok.Getter;
import org.mockito.Mockito;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@Builder
@Getter
public class MockedChunkJob<I, O> extends BaseChunkJob<I, O> {

    public static final String TEST_JOB_NAME = "__TEST_JOB__";

    @Builder.Default
    @Nonnull
    protected BatchConfiguration.JobConfiguration jobConfiguration = new BatchConfiguration.JobConfiguration();

    @Builder.Default
    @Nonnull
    protected String jobName = TEST_JOB_NAME;

    @SuppressWarnings("unchecked")
    @Builder.Default
    @Nonnull
    protected ItemReader<I> itemReader = Mockito.mock(ItemReader.class);

    @SuppressWarnings("unchecked")
    @Builder.Default
    @Nonnull
    protected ItemProcessor<I, O> itemProcessor = Mockito.mock(ItemProcessor.class);

    @SuppressWarnings("unchecked")
    @Builder.Default
    @Nonnull
    protected ItemWriter<O> itemWriter = Mockito.mock(ItemWriter.class);
}
