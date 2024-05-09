/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.services.DataSetService;
import fr.dademo.batch.tools.batch.job.ChunkedStepProvider;
import fr.dademo.batch.tools.batch.job.SimpleChunkedStepProvider;
import fr.dademo.batch.tools.batch.job.tasklets.DataSetResourceQueryTasklet;
import fr.dademo.batch.tools.batch.job.tasklets.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.CompanyTable;
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
@Component(JobDefinition.COMPANY_JOB_NAME)
public class JobDefinition extends AbstractApplicationStgJob {

    public static final String COMPANY_CONFIG_JOB_NAME = "company";
    public static final String COMPANY_NORMALIZED_CONFIG_JOB_NAME = "company";
    public static final String COMPANY_JOB_NAME = "stg_" + COMPANY_NORMALIZED_CONFIG_JOB_NAME;
    public static final String COMPANY_MIGRATION_FOLDER = "stg/company";

    private static final String DATASET_TITLE = "base-sirene-des-entreprises-et-de-leurs-etablissements-siren-siret";
    private static final String DATA_TITLE_PREFIX = "Sirene : Fichier StockEtablissement ";


    private final CompanyItemReader companyItemReader;
    private final CompanyItemMapper companyItemMapper;
    private final CompanyItemWriter companyItemWriter;
    private final DataGouvFrDataQuerierService dataGouvFrDataQuerierService;

    public JobDefinition(
        // Common job resources
        @Nonnull JobRepository jobRepository,
        @Nonnull @Qualifier(BATCH_DATA_SOURCE_TRANSACTION_MANAGER_BEAN_NAME) PlatformTransactionManager platformTransactionManager,
        @Nonnull BatchConfiguration batchConfiguration,
        @Nonnull BatchDataSourcesConfiguration batchDataSourcesConfiguration,
        @Nonnull DataSourcesFactory dataSourcesFactory,
        @Nonnull ResourceLoader resourceLoader,
        @Nonnull DataSetService dataSetService,
        @Nonnull DataGouvFrDataQuerierService dataGouvFrDataQuerierService,
        // Job-specific
        @Nonnull CompanyItemReader companyItemReader,
        @Nonnull CompanyItemMapper companyItemMapper,
        @Nonnull CompanyItemWriter companyItemWriter) {

        super(
            jobRepository,
            platformTransactionManager,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader,
            dataSetService
        );

        this.companyItemReader = companyItemReader;
        this.companyItemMapper = companyItemMapper;
        this.companyItemWriter = companyItemWriter;
        this.dataGouvFrDataQuerierService = dataGouvFrDataQuerierService;
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_JOB_NAME;
    }

    @Override
    protected ChunkedStepProvider getChunkedStepProvider() {

        return new SimpleChunkedStepProvider<>(
            getJobRepository(),
            getPlatformTransactionManager(),
            companyItemReader,
            companyItemMapper,
            companyItemWriter,
            getStepExecutionListeners()
        );
    }

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return getBatchConfiguration().getJobConfigurationByName(COMPANY_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    protected ItemWriter<?> getItemWriter() {
        return companyItemWriter;
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
            .filter(DataGouvFrFilterHelpers.fieldStartingWith(DataGouvFrDataSetResource::getTitle, DATA_TITLE_PREFIX))
            .max(Comparator.comparing(DataGouvFrDataSetResource::dateTimeKeyExtractor))
            .orElseThrow(() -> new ResourceNotFoundException(DATA_TITLE_PREFIX + "*", dataGouvFrDataSet));
    }

    @Override
    protected Tasklet getJooqTruncateTasklet() {

        return new JooqTruncateTasklet<>(
            getJobOutputDslContext(),
            new CompanyTable(getJobOutputDataSourceSchema())
        );
    }

    @Nullable
    @Override
    protected String getLiquibaseMigrationFolder() {
        return COMPANY_MIGRATION_FOLDER;
    }
}
