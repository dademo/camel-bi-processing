/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal_history;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.batch.tools.batch.job.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistoryTable;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.writers.CompanyLegalHistoryJdbcItemWriterImpl;
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
@Component(JobDefinition.COMPANY_LEGAL_HISTORY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<WrappedRowResource, CompanyLegalHistory> {

    public static final String COMPANY_LEGAL_HISTORY_CONFIG_JOB_NAME = "company-legal-history";
    public static final String COMPANY_LEGAL_HISTORY_NORMALIZED_CONFIG_JOB_NAME = "company_legal_history";
    public static final String COMPANY_LEGAL_HISTORY_JOB_NAME = "stg_" + COMPANY_LEGAL_HISTORY_NORMALIZED_CONFIG_JOB_NAME;
    public static final String COMPANY_LEGAL_HISTORY_MIGRATION_FOLDER = "stg/company_legal_history";
    public static final String COMPANY_LEGAL_HISTORY_DEFAULT_JDBC_DATA_SOURCE_NAME = "stg";
    private final CompanyLegalHistoryItemReader companyLegalHistoryItemReader;
    private final CompanyLegalHistoryItemMapper companyLegalHistoryItemMapper;
    private final CompanyLegalHistoryItemWriter companyLegalHistoryItemWriter;

    public JobDefinition(
        // Common job resources
        JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration,
        DataSourcesFactory dataSourcesFactory,
        ResourceLoader resourceLoader,
        // Job-specific
        CompanyLegalHistoryItemReader companyLegalHistoryItemReader,
        CompanyLegalHistoryItemMapper companyLegalHistoryItemMapper,
        CompanyLegalHistoryItemWriter companyLegalHistoryItemWriter) {

        super(jobBuilderFactory,
            stepBuilderFactory,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader);

        this.companyLegalHistoryItemReader = companyLegalHistoryItemReader;
        this.companyLegalHistoryItemMapper = companyLegalHistoryItemMapper;
        this.companyLegalHistoryItemWriter = companyLegalHistoryItemWriter;
    }

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return getBatchConfiguration().getJobConfigurationByName(COMPANY_LEGAL_HISTORY_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_LEGAL_HISTORY_JOB_NAME;
    }

    @Nonnull
    @Override
    protected List<Tasklet> getInitTasks() {

        if (companyLegalHistoryItemWriter instanceof CompanyLegalHistoryJdbcItemWriterImpl) {

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
            new CompanyLegalHistoryTable(getJobOutputDataSourceSchema())
        );
    }

    @Nullable
    @Override
    protected String getMigrationFolder() {
        return COMPANY_LEGAL_HISTORY_MIGRATION_FOLDER;
    }

    @Nonnull
    @Override
    public ItemReader<WrappedRowResource> getItemReader() {
        return companyLegalHistoryItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<WrappedRowResource, CompanyLegalHistory> getItemProcessor() {
        return companyLegalHistoryItemMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<CompanyLegalHistory> getItemWriter() {
        return companyLegalHistoryItemWriter;
    }
}
