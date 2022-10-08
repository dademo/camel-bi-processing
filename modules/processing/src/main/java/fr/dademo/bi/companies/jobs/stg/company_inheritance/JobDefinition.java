/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.services.DataSetService;
import fr.dademo.batch.tools.batch.job.ChunkedStepProvider;
import fr.dademo.batch.tools.batch.job.SimpleChunkedStepProvider;
import fr.dademo.batch.tools.batch.job.tasklets.DataSetResourceQueryTasklet;
import fr.dademo.batch.tools.batch.job.tasklets.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritanceTable;
import fr.dademo.bi.companies.shared.AbstractApplicationStgJob;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.helpers.DataGouvFrFilterHelpers;
import fr.dademo.data.helpers.data_gouv_fr.repository.DataGouvFrDataQuerierService;
import fr.dademo.data.helpers.data_gouv_fr.repository.exception.ResourceNotFoundException;
import lombok.SneakyThrows;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;

/**
 * @author dademo
 */
@SuppressWarnings({"java:S107", "java:S110"})
@Component(JobDefinition.COMPANY_INHERITANCE_JOB_NAME)
public class JobDefinition extends AbstractApplicationStgJob {

    public static final String COMPANY_INHERITANCE_CONFIG_JOB_NAME = "company-inheritance";
    public static final String COMPANY_INHERITANCE_NORMALIZED_CONFIG_JOB_NAME = "company_inheritance";
    public static final String COMPANY_INHERITANCE_JOB_NAME = "stg_" + COMPANY_INHERITANCE_NORMALIZED_CONFIG_JOB_NAME;
    public static final String COMPANY_INHERITANCE_MIGRATION_FOLDER = "stg/company_inheritance";
    private static final String DATASET_TITLE = "base-sirene-des-entreprises-et-de-leurs-etablissements-siren-siret";
    private static final String DATA_TITLE_PREFIX = "Sirene : Fichier StockEtablissementLiensSuccession ";


    private final StepBuilderFactory stepBuilderFactory;
    private final CompanyInheritanceItemReader companyInheritanceItemReader;
    private final CompanyInheritanceItemMapper companyInheritanceItemMapper;
    private final CompanyInheritanceItemWriter companyInheritanceItemWriter;
    private final DataGouvFrDataQuerierService dataGouvFrDataQuerierService;

    public JobDefinition(
        // Common job resources
        JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration,
        DataSourcesFactory dataSourcesFactory,
        ResourceLoader resourceLoader,
        DataSetService dataSetService,
        DataGouvFrDataQuerierService dataGouvFrDataQuerierService,
        // Job-specific
        CompanyInheritanceItemReader companyInheritanceItemReader,
        CompanyInheritanceItemMapper companyInheritanceItemMapper,
        CompanyInheritanceItemWriter companyInheritanceItemWriter) {

        super(
            jobBuilderFactory,
            stepBuilderFactory,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader,
            dataSetService
        );

        this.stepBuilderFactory = stepBuilderFactory;
        this.companyInheritanceItemReader = companyInheritanceItemReader;
        this.companyInheritanceItemMapper = companyInheritanceItemMapper;
        this.companyInheritanceItemWriter = companyInheritanceItemWriter;
        this.dataGouvFrDataQuerierService = dataGouvFrDataQuerierService;
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_INHERITANCE_JOB_NAME;
    }

    @Override
    protected ChunkedStepProvider getChunkedStepProvider() {

        return new SimpleChunkedStepProvider<>(
            stepBuilderFactory,
            companyInheritanceItemReader,
            companyInheritanceItemMapper,
            companyInheritanceItemWriter,
            getStepExecutionListeners()
        );
    }

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return getBatchConfiguration().getJobConfigurationByName(COMPANY_INHERITANCE_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    protected ItemWriter<?> getItemWriter() {
        return companyInheritanceItemWriter;
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
            new CompanyInheritanceTable(getJobOutputDataSourceSchema())
        );
    }

    @Nullable
    @Override
    protected String getLiquibaseMigrationFolder() {
        return COMPANY_INHERITANCE_MIGRATION_FOLDER;
    }
}
