/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.batch.tools.batch.job.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.AssociationTable;
import fr.dademo.bi.companies.jobs.stg.association.writers.AssociationJdbcItemWriterImpl;
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
@Component(JobDefinition.ASSOCIATION_JOB_NAME)
public class JobDefinition extends BaseChunkJob<WrappedRowResource, Association> {

    public static final String ASSOCIATION_CONFIG_JOB_NAME = "association";
    public static final String ASSOCIATION_NORMALIZED_CONFIG_JOB_NAME = "association";
    public static final String ASSOCIATION_JOB_NAME = "stg_" + ASSOCIATION_NORMALIZED_CONFIG_JOB_NAME;
    public static final String ASSOCIATION_MIGRATION_FOLDER = "stg/association";
    private final AssociationItemReader associationItemReader;
    private final AssociationItemMapper associationItemMapper;
    private final AssociationItemWriter associationItemWriter;

    public JobDefinition(
        // Common job resources
        JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration,
        DataSourcesFactory dataSourcesFactory,
        ResourceLoader resourceLoader,
        // Job-specific
        AssociationItemReader associationItemReader,
        AssociationItemMapper associationItemMapper,
        AssociationItemWriter associationItemWriter) {

        super(jobBuilderFactory,
            stepBuilderFactory,
            batchConfiguration,
            batchDataSourcesConfiguration,
            dataSourcesFactory,
            resourceLoader);

        this.associationItemReader = associationItemReader;
        this.associationItemMapper = associationItemMapper;
        this.associationItemWriter = associationItemWriter;
    }

    @Nonnull
    @Override
    public String getJobName() {
        return ASSOCIATION_JOB_NAME;
    }

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return getBatchConfiguration().getJobConfigurationByName(ASSOCIATION_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    protected List<Tasklet> getInitTasks() {

        if (associationItemWriter instanceof AssociationJdbcItemWriterImpl) {

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
            new AssociationTable(getJobOutputDataSourceSchema())
        );
    }

    @Nullable
    @Override
    protected String getMigrationFolder() {
        return ASSOCIATION_MIGRATION_FOLDER;
    }

    @Nonnull
    @Override
    public ItemReader<WrappedRowResource> getItemReader() {
        return associationItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<WrappedRowResource, Association> getItemProcessor() {
        return associationItemMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<Association> getItemWriter() {
        return associationItemWriter;
    }
}
