/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.testing.batch;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.tools.batch.job.BaseChunkedJob;
import fr.dademo.batch.tools.batch.job.ChunkedStepProvider;
import lombok.Builder;
import lombok.Getter;
import org.mockito.Mockito;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Nonnull;

import static fr.dademo.batch.beans.BeanValues.BATCH_DATA_SOURCE_TRANSACTION_MANAGER_BEAN_NAME;

/**
 * @author dademo
 */
@SuppressWarnings("unchecked")
@Getter
public class MockedChunkJob<I, O> extends BaseChunkedJob {

    public static final String TEST_JOB_NAME = "__TEST_JOB__";
    @Nonnull
    protected BatchConfiguration.JobConfiguration jobConfiguration = new BatchConfiguration.JobConfiguration();
    @Nonnull
    protected String jobName = TEST_JOB_NAME;
    @Nonnull
    protected ItemReader<I> itemReader = Mockito.mock(ItemReader.class);
    @Nonnull
    protected ItemProcessor<I, O> itemProcessor = Mockito.mock(ItemProcessor.class);
    @Nonnull
    protected ItemWriter<O> itemWriter = Mockito.mock(ItemWriter.class);

    @Builder
    public MockedChunkJob(
        @Nonnull JobRepository jobRepository,
        @Nonnull @Qualifier(BATCH_DATA_SOURCE_TRANSACTION_MANAGER_BEAN_NAME) PlatformTransactionManager platformTransactionManager,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration,
        DataSourcesFactory dataSourcesFactory,
        ResourceLoader resourceLoader) {
        super(
            jobRepository,
            platformTransactionManager,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader
        );
    }

    @Override
    protected ChunkedStepProvider getChunkedStepProvider() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
