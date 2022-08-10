/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.batch.tools.batch.job.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.exceptions.MissingBeanException;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import fr.dademo.bi.companies.jobs.stg.association.writers.AssociationJdbcItemWriterImpl;
import org.jooq.DSLContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static fr.dademo.batch.beans.BeanValues.STG_DATA_SOURCE_DSL_CONTEXT_BEAN_NAME;
import static fr.dademo.bi.companies.jobs.stg.association.datamodel.AssociationTable.ASSOCIATION;

/**
 * @author dademo
 */
@Component(JobDefinition.ASSOCIATION_JOB_NAME)
public class JobDefinition extends BaseChunkJob<WrappedRowResource, Association> {

    public static final String ASSOCIATION_CONFIG_JOB_NAME = "association";
    public static final String ASSOCIATION_NORMALIZED_CONFIG_JOB_NAME = "association";
    public static final String ASSOCIATION_JOB_NAME = "stg_" + ASSOCIATION_NORMALIZED_CONFIG_JOB_NAME;
    public static final String ASSOCIATION_MIGRATION_FOLDER = "stg/association";
    public static final String ASSOCIATION_DEFAULT_JDBC_DATA_SOURCE_NAME = "stg";

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Nullable
    @Autowired(required = false)
    @Qualifier(STG_DATA_SOURCE_DSL_CONTEXT_BEAN_NAME)
    private DSLContext dslContext;

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

    @Nonnull
    @Override
    protected List<Tasklet> getInitTasks() {

        if (associationItemWriter instanceof AssociationJdbcItemWriterImpl) {

            return Arrays.asList(
                getLiquibaseMigrationTasklet(),
                getJooqTruncateTasklet()
            );
        } else {
            return Collections.emptyList();
        }
    }

    private Tasklet getJooqTruncateTasklet() {

        return new JooqTruncateTasklet<>(
            Optional.ofNullable(dslContext)
                .orElseThrow(() -> new MissingBeanException(DSLContext.class, STG_DATA_SOURCE_DSL_CONTEXT_BEAN_NAME)),
            ASSOCIATION
        );
    }

    @Nullable
    @Override
    protected String getDefaultJdbcDataSourceName() {
        return ASSOCIATION_DEFAULT_JDBC_DATA_SOURCE_NAME;
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
