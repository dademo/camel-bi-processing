/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association_waldec;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.services.DataSetService;
import fr.dademo.batch.tools.batch.job.ChunkedStepProvider;
import fr.dademo.batch.tools.batch.job.SimpleChunkedStepProvider;
import fr.dademo.batch.tools.batch.job.tasklets.DataSetResourceQueryTasklet;
import fr.dademo.batch.tools.batch.job.tasklets.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldecTable;
import fr.dademo.bi.companies.shared.AbstractApplicationStgJob;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.helpers.DataGouvFrFilterHelpers;
import fr.dademo.data.helpers.data_gouv_fr.repository.DataGouvFrDataQuerierService;
import fr.dademo.data.helpers.data_gouv_fr.repository.exception.ResourceNotFoundException;
import lombok.SneakyThrows;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;

import static fr.dademo.batch.beans.BeanValues.BATCH_DATA_SOURCE_TRANSACTION_MANAGER_BEAN_NAME;

/**
 * @author dademo
 */
@SuppressWarnings({"java:S107", "java:S110"})
@Component(JobDefinition.ASSOCIATION_WALDEC_JOB_NAME)
public class JobDefinition extends AbstractApplicationStgJob {

    public static final String ASSOCIATION_WALDEC_CONFIG_JOB_NAME = "association-waldec";
    public static final String ASSOCIATION_WALDEC_NORMALIZED_CONFIG_JOB_NAME = "association_waldec";
    public static final String ASSOCIATION_WALDEC_JOB_NAME = "stg_" + ASSOCIATION_WALDEC_NORMALIZED_CONFIG_JOB_NAME;
    public static final String ASSOCIATION_WALDEC_MIGRATION_FOLDER = "stg/association_waldec";

    private static final String DATASET_TITLE = "repertoire-national-des-associations";
    private static final String DATA_TITLE_WALDEC = " Waldec ";

    private final AssociationWaldecItemReader associationWaldecItemReader;
    private final AssociationWaldecItemMapper associationWaldecItemMapper;
    private final AssociationWaldecItemWriter associationWaldecItemWriter;
    private final DataGouvFrDataQuerierService dataGouvFrDataQuerierService;

    public JobDefinition(
        // Common job resources
        @Nonnull JobRepository jobRepository,
        @Nonnull @Qualifier(BATCH_DATA_SOURCE_TRANSACTION_MANAGER_BEAN_NAME) PlatformTransactionManager platformTransactionManager,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration,
        DataSourcesFactory dataSourcesFactory,
        ResourceLoader resourceLoader,
        DataSetService dataSetService,
        DataGouvFrDataQuerierService dataGouvFrDataQuerierService,
        // Job-specific
        AssociationWaldecItemReader associationWaldecItemReader,
        AssociationWaldecItemMapper associationWaldecItemMapper,
        AssociationWaldecItemWriter associationWaldecItemWriter) {

        super(
            jobRepository,
            platformTransactionManager,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader,
            dataSetService
        );

        this.associationWaldecItemReader = associationWaldecItemReader;
        this.associationWaldecItemMapper = associationWaldecItemMapper;
        this.associationWaldecItemWriter = associationWaldecItemWriter;
        this.dataGouvFrDataQuerierService = dataGouvFrDataQuerierService;
    }

    @Nonnull
    @Override
    public String getJobName() {
        return ASSOCIATION_WALDEC_JOB_NAME;
    }

    @Override
    protected ChunkedStepProvider getChunkedStepProvider() {

        return new SimpleChunkedStepProvider<>(
            getJobRepository(),
            getPlatformTransactionManager(),
            associationWaldecItemReader,
            associationWaldecItemMapper,
            associationWaldecItemWriter,
            getStepExecutionListeners()
        );
    }

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return getBatchConfiguration().getJobConfigurationByName(ASSOCIATION_WALDEC_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    protected ItemWriter<?> getItemWriter() {
        return associationWaldecItemWriter;
    }

    @Override
    protected DataSetResourceQueryTasklet.DataSetResourceProvider getDataSetResourceProvider() {
        return this::getDataSetResource;
    }

    @SneakyThrows
    private DataGouvFrDataSetResource getDataSetResource() {

        final var dataGouvFrDataSet = dataGouvFrDataQuerierService.getDataSet(DATASET_TITLE);
        return dataGouvFrDataSet
            .getResources().stream()
            .filter(DataGouvFrFilterHelpers.fieldContaining(DataGouvFrDataSetResource::getTitle, DATA_TITLE_WALDEC))
            .max(Comparator.comparing(DataGouvFrDataSetResource::dateTimeKeyExtractor))
            .orElseThrow(() -> new ResourceNotFoundException("*" + DATA_TITLE_WALDEC + "*", dataGouvFrDataSet));

    }

    @Override
    protected Tasklet getJooqTruncateTasklet() {

        return new JooqTruncateTasklet<>(
            getJobOutputDslContext(),
            new AssociationWaldecTable(getJobOutputDataSourceSchema())
        );
    }

    @Nullable
    @Override
    protected String getLiquibaseMigrationFolder() {
        return ASSOCIATION_WALDEC_MIGRATION_FOLDER;
    }
}
