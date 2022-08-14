/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_history;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.batch.tools.batch.job.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistoryTable;
import fr.dademo.bi.companies.jobs.stg.company_history.writers.CompanyHistoryJdbcItemWriterImpl;
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
@Component(JobDefinition.COMPANY_HISTORY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<WrappedRowResource, CompanyHistory> {

    public static final String COMPANY_HISTORY_CONFIG_JOB_NAME = "company-history";
    public static final String COMPANY_HISTORY_NORMALIZED_CONFIG_JOB_NAME = "company_history";
    public static final String COMPANY_HISTORY_JOB_NAME = "stg_" + COMPANY_HISTORY_NORMALIZED_CONFIG_JOB_NAME;
    public static final String COMPANY_HISTORY_MIGRATION_FOLDER = "stg/company_history";
    public static final String COMPANY_HISTORY_DEFAULT_JDBC_DATA_SOURCE_NAME = "stg";
    private final CompanyHistoryItemReader companyHistoryItemReader;
    private final CompanyHistoryItemMapper companyHistoryItemMapper;
    private final CompanyHistoryItemWriter companyHistoryItemWriter;

    public JobDefinition(
        // Common job resources
        JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration,
        DataSourcesFactory dataSourcesFactory,
        ResourceLoader resourceLoader,
        // Job-specific
        CompanyHistoryItemReader companyHistoryItemReader,
        CompanyHistoryItemMapper companyHistoryItemMapper,
        CompanyHistoryItemWriter companyHistoryItemWriter) {

        super(jobBuilderFactory,
            stepBuilderFactory,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader);

        this.companyHistoryItemReader = companyHistoryItemReader;
        this.companyHistoryItemMapper = companyHistoryItemMapper;
        this.companyHistoryItemWriter = companyHistoryItemWriter;
    }

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return getBatchConfiguration().getJobConfigurationByName(COMPANY_HISTORY_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_HISTORY_JOB_NAME;
    }

    @Nonnull
    @Override
    protected List<Tasklet> getInitTasks() {

        if (companyHistoryItemWriter instanceof CompanyHistoryJdbcItemWriterImpl) {

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
            new CompanyHistoryTable(getJobOutputDataSourceSchema())
        );
    }

    @Nullable
    @Override
    protected String getMigrationFolder() {
        return COMPANY_HISTORY_MIGRATION_FOLDER;
    }

    @Nonnull
    @Override
    public ItemReader<WrappedRowResource> getItemReader() {
        return companyHistoryItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<WrappedRowResource, CompanyHistory> getItemProcessor() {
        return companyHistoryItemMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<CompanyHistory> getItemWriter() {
        return companyHistoryItemWriter;
    }
}
