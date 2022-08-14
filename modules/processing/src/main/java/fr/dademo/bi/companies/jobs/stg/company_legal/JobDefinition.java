/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.batch.tools.batch.job.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegal;
import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegalTable;
import fr.dademo.bi.companies.jobs.stg.company_legal.writers.CompanyLegalJdbcItemWriterImpl;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author dademo
 */
@Component(JobDefinition.COMPANY_LEGAL_JOB_NAME)
public class JobDefinition extends BaseChunkJob<WrappedRowResource, CompanyLegal> {

    public static final String COMPANY_LEGAL_CONFIG_JOB_NAME = "company-legal";
    public static final String COMPANY_LEGAL_NORMALIZED_CONFIG_JOB_NAME = "company_legal";
    public static final String COMPANY_LEGAL_JOB_NAME = "stg_" + COMPANY_LEGAL_NORMALIZED_CONFIG_JOB_NAME;
    public static final String COMPANY_LEGAL_MIGRATION_FOLDER = "stg/company_legal";
    public static final String COMPANY_LEGAL_DEFAULT_JDBC_DATA_SOURCE_NAME = "stg";
    private final CompanyLegalItemReader companyLegalItemReader;
    private final CompanyLegalItemMapper companyLegalItemMapper;
    private final CompanyLegalItemWriter companyLegalItemWriter;

    public JobDefinition(
        // Common job resources
        JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration,
        DataSourcesFactory dataSourcesFactory,
        ResourceLoader resourceLoader,
        // Job-specific
        CompanyLegalItemReader companyLegalItemReader,
        CompanyLegalItemMapper companyLegalItemMapper,
        CompanyLegalItemWriter companyLegalItemWriter) {

        super(jobBuilderFactory,
            stepBuilderFactory,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader);

        this.companyLegalItemReader = companyLegalItemReader;
        this.companyLegalItemMapper = companyLegalItemMapper;
        this.companyLegalItemWriter = companyLegalItemWriter;
    }

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return getBatchConfiguration().getJobConfigurationByName(COMPANY_LEGAL_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_LEGAL_JOB_NAME;
    }

    @Nonnull
    @Override
    protected List<Tasklet> getInitTasks() {

        if (companyLegalItemWriter instanceof CompanyLegalJdbcItemWriterImpl) {

            return Arrays.asList(
                getLiquibaseOutputMigrationTasklet(),
                getJooqTruncateTasklet()
            );
        } else {
            return Collections.emptyList();
        }
    }

    private Tasklet getJooqTruncateTasklet() {

        return new JooqTruncateTasklet<>(
            getJobOutputDslContext(),
            new CompanyLegalTable(getJobOutputDataSourceSchema())
        );
    }

    @Nullable
    @Override
    protected String getMigrationFolder() {
        return COMPANY_LEGAL_MIGRATION_FOLDER;
    }

    @Nonnull
    @Override
    public ItemReader<WrappedRowResource> getItemReader() {
        return companyLegalItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<WrappedRowResource, CompanyLegal> getItemProcessor() {
        return companyLegalItemMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<CompanyLegal> getItemWriter() {

        return companyLegalItemWriter;
    }
}
