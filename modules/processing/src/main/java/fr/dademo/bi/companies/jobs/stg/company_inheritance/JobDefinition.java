/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.batch.tools.batch.job.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritanceTable;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.writers.CompanyInheritanceJdbcItemWriterImpl;
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
@Component(JobDefinition.COMPANY_INHERITANCE_JOB_NAME)
public class JobDefinition extends BaseChunkJob<WrappedRowResource, CompanyInheritance> {

    public static final String COMPANY_INHERITANCE_CONFIG_JOB_NAME = "company-inheritance";
    public static final String COMPANY_INHERITANCE_NORMALIZED_CONFIG_JOB_NAME = "company_inheritance";
    public static final String COMPANY_INHERITANCE_JOB_NAME = "stg_" + COMPANY_INHERITANCE_NORMALIZED_CONFIG_JOB_NAME;
    public static final String COMPANY_INHERITANCE_MIGRATION_FOLDER = "stg/company_inheritance";
    public static final String COMPANY_INHERITANCE_DEFAULT_JDBC_DATA_SOURCE_NAME = "stg";
    private final CompanyInheritanceItemReader companyInheritanceItemReader;
    private final CompanyInheritanceItemMapper companyInheritanceItemMapper;
    private final CompanyInheritanceItemWriter companyInheritanceItemWriter;

    public JobDefinition(
        // Common job resources
        JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration,
        DataSourcesFactory dataSourcesFactory,
        ResourceLoader resourceLoader,
        // Job-specific
        CompanyInheritanceItemReader companyInheritanceItemReader,
        CompanyInheritanceItemMapper companyInheritanceItemMapper,
        CompanyInheritanceItemWriter companyInheritanceItemWriter) {

        super(jobBuilderFactory,
            stepBuilderFactory,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader);

        this.companyInheritanceItemReader = companyInheritanceItemReader;
        this.companyInheritanceItemMapper = companyInheritanceItemMapper;
        this.companyInheritanceItemWriter = companyInheritanceItemWriter;
    }

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return getBatchConfiguration().getJobConfigurationByName(COMPANY_INHERITANCE_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_INHERITANCE_JOB_NAME;
    }

    @Nonnull
    @Override
    protected List<Tasklet> getInitTasks() {

        if (companyInheritanceItemWriter instanceof CompanyInheritanceJdbcItemWriterImpl) {

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
            new CompanyInheritanceTable(getJobOutputDataSourceSchema())
        );
    }

    @Nullable
    @Override
    protected String getMigrationFolder() {
        return COMPANY_INHERITANCE_MIGRATION_FOLDER;
    }

    @Nonnull
    @Override
    public ItemReader<WrappedRowResource> getItemReader() {
        return companyInheritanceItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<WrappedRowResource, CompanyInheritance> getItemProcessor() {
        return companyInheritanceItemMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<CompanyInheritance> getItemWriter() {
        return companyInheritanceItemWriter;
    }
}
