/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.batch.tools.batch.job.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.CompanyTable;
import fr.dademo.bi.companies.jobs.stg.company.writers.CompanyJdbcItemWriterImpl;
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
@Component(JobDefinition.COMPANY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<WrappedRowResource, Company> {

    public static final String COMPANY_CONFIG_JOB_NAME = "company";
    public static final String COMPANY_NORMALIZED_CONFIG_JOB_NAME = "company";
    public static final String COMPANY_JOB_NAME = "stg_" + COMPANY_NORMALIZED_CONFIG_JOB_NAME;
    public static final String COMPANY_MIGRATION_FOLDER = "stg/company";
    public static final String COMPANY_DEFAULT_JDBC_DATA_SOURCE_NAME = "stg";
    private final CompanyItemReader companyItemReader;
    private final CompanyItemMapper companyItemMapper;
    private final CompanyItemWriter companyItemWriter;

    public JobDefinition(
        // Common job resources
        JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration,
        DataSourcesFactory dataSourcesFactory,
        ResourceLoader resourceLoader,
        // Job-specific
        CompanyItemReader companyItemReader,
        CompanyItemMapper companyItemMapper,
        CompanyItemWriter companyItemWriter) {

        super(jobBuilderFactory,
            stepBuilderFactory,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader);

        this.companyItemReader = companyItemReader;
        this.companyItemMapper = companyItemMapper;
        this.companyItemWriter = companyItemWriter;
    }

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return getBatchConfiguration().getJobConfigurationByName(COMPANY_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_JOB_NAME;
    }

    @Nonnull
    @Override
    protected List<Tasklet> getInitTasks() {

        if (companyItemWriter instanceof CompanyJdbcItemWriterImpl) {

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
            new CompanyTable(getJobOutputDataSourceSchema())
        );
    }

    @Nullable
    @Override
    protected String getMigrationFolder() {
        return COMPANY_MIGRATION_FOLDER;
    }

    @Nonnull
    @Override
    public ItemReader<WrappedRowResource> getItemReader() {
        return companyItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<WrappedRowResource, Company> getItemProcessor() {
        return companyItemMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<Company> getItemWriter() {
        return companyItemWriter;
    }
}
