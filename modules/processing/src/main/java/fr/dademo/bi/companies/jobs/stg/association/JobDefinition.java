/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import fr.dademo.bi.companies.jobs.stg.association.writers.AssociationJdbcItemWriterImpl;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author dademo
 */
@Component(JobDefinition.ASSOCIATION_JOB_NAME)
public class JobDefinition extends BaseChunkJob<WrappedRowResource, Association> {

    public static final String ASSOCIATION_CONFIG_JOB_NAME = "association";
    public static final String ASSOCIATION_NORMALIZED_CONFIG_JOB_NAME = "association";
    public static final String ASSOCIATION_JOB_NAME = "stg_" + ASSOCIATION_NORMALIZED_CONFIG_JOB_NAME;
    public static final String ASSOCIATION_MIGRATION_FOLDER = "stg/association";
    public static final String DEFAULT_JDBC_DATA_SOURCE_NAME = "stg";

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private AssociationItemReader associationItemReader;
    @Autowired
    private AssociationItemMapper associationItemMapper;
    @Autowired
    private AssociationItemWriter associationItemWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(ASSOCIATION_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return ASSOCIATION_JOB_NAME;
    }

    @Nullable
    @Override
    protected Tasklet getInitTask() {
        return (associationItemWriter instanceof AssociationJdbcItemWriterImpl) ? getLiquibaseMigrationTasklet() : null;
    }

    @Nullable
    @Override
    protected String getDefaultJdbcDataSourceName() {
        return DEFAULT_JDBC_DATA_SOURCE_NAME;
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
