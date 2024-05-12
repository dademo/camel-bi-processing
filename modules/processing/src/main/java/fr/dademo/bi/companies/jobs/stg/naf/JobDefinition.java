/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.services.DataSetService;
import fr.dademo.batch.tools.batch.job.ChunkedStepProvider;
import fr.dademo.batch.tools.batch.job.SimpleChunkedStepProvider;
import fr.dademo.batch.tools.batch.job.tasklets.DataSetResourceQueryTasklet;
import fr.dademo.batch.tools.batch.job.tasklets.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionTable;
import fr.dademo.bi.companies.shared.AbstractApplicationStgJob;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.helpers.DataGouvFrFilterHelpers;
import fr.dademo.data.helpers.data_gouv_fr.repository.DataGouvFrDataQuerierService;
import fr.dademo.data.helpers.data_gouv_fr.repository.exception.ResourceNotFoundException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.SneakyThrows;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Comparator;

import static fr.dademo.batch.beans.BeanValues.BATCH_DATA_SOURCE_TRANSACTION_MANAGER_BEAN_NAME;
import static fr.dademo.batch.beans.BeanValues.STG_DATA_SOURCE_NAME;

/**
 * @author dademo
 */
@SuppressWarnings({"java:S107", "java:S110"})
@Component(JobDefinition.NAF_JOB_NAME)
public class JobDefinition extends AbstractApplicationStgJob {

    public static final String NAF_CONFIG_JOB_NAME = "naf";
    public static final String NAF_NORMALIZED_CONFIG_JOB_NAME = "Naf";
    public static final String NAF_JOB_NAME = STG_DATA_SOURCE_NAME + NAF_NORMALIZED_CONFIG_JOB_NAME;
    public static final String NAF_MIGRATION_FOLDER = "stg/naf";

    private static final String DATASET_TITLE = "nomenclature-dactivites-francaise-naf-rev-2-code-ape";
    private static final String DATASET_RESOURCE_TITLE = "Export au format JSON";


    private final NafDefinitionItemReader nafDefinitionItemReader;
    private final NafDefinitionItemProcessor nafDefinitionItemProcessor;
    private final NafDefinitionItemWriter nafDefinitionItemWriter;
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
        NafDefinitionItemReader nafDefinitionItemReader,
        NafDefinitionItemProcessor nafDefinitionItemProcessor,
        NafDefinitionItemWriter nafDefinitionItemWriter) {

        super(
            jobRepository,
            platformTransactionManager,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader,
            dataSetService
        );

        this.nafDefinitionItemReader = nafDefinitionItemReader;
        this.nafDefinitionItemProcessor = nafDefinitionItemProcessor;
        this.nafDefinitionItemWriter = nafDefinitionItemWriter;
        this.dataGouvFrDataQuerierService = dataGouvFrDataQuerierService;
    }

    @Nonnull
    @Override
    public String getJobName() {
        return NAF_JOB_NAME;
    }

    @Override
    protected ChunkedStepProvider getChunkedStepProvider() {

        return new SimpleChunkedStepProvider<>(
            getJobRepository(),
            getPlatformTransactionManager(),
            nafDefinitionItemReader,
            nafDefinitionItemProcessor,
            nafDefinitionItemWriter,
            getStepExecutionListeners()
        );
    }

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return getBatchConfiguration().getJobConfigurationByName(NAF_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    protected ItemWriter<?> getItemWriter() {
        return nafDefinitionItemWriter;
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
            .filter(DataGouvFrFilterHelpers.fieldEquals(DataGouvFrDataSetResource::getTitle, DATASET_RESOURCE_TITLE))
            .max(Comparator.comparing(DataGouvFrDataSetResource::dateTimeKeyExtractor))
            .orElseThrow(() -> new ResourceNotFoundException(DATASET_RESOURCE_TITLE, dataGouvFrDataSet));
    }

    @Override
    protected Tasklet getJooqTruncateTasklet() {

        return new JooqTruncateTasklet<>(
            getJobOutputDslContext(),
            new NafDefinitionTable(getJobOutputDataSourceSchema())
        );
    }

    @Nullable
    @Override
    protected String getLiquibaseMigrationFolder() {
        return NAF_MIGRATION_FOLDER;
    }
}
