/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.batch.tools.batch.job.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.exceptions.MissingBeanException;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import fr.dademo.bi.companies.jobs.stg.naf.writers.NafJdbcDefinitionItemWriterImpl;
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
import static fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionTable.NAF_DEFINITION;

/**
 * @author dademo
 */
@Component(JobDefinition.NAF_JOB_NAME)
public class JobDefinition extends BaseChunkJob<NafDefinitionContainer, NafDefinition> {

    public static final String NAF_CONFIG_JOB_NAME = "naf";
    public static final String NAF_NORMALIZED_CONFIG_JOB_NAME = "naf";
    public static final String NAF_JOB_NAME = "stg_" + NAF_NORMALIZED_CONFIG_JOB_NAME;
    public static final String NAF_MIGRATION_FOLDER = "stg/naf";
    public static final String NAF_DEFAULT_JDBC_DATA_SOURCE_NAME = "stg";

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Nullable
    @Autowired(required = false)
    @Qualifier(STG_DATA_SOURCE_DSL_CONTEXT_BEAN_NAME)
    private DSLContext dslContext;

    @Autowired
    private NafDefinitionItemReader nafDefinitionItemReader;
    @Autowired
    private NafDefinitionItemProcessor nafDefinitionItemProcessor;
    @Autowired
    private NafDefinitionItemWriter nafDefinitionItemWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(NAF_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return NAF_JOB_NAME;
    }

    @Nonnull
    @Override
    protected List<Tasklet> getInitTasks() {

        if (nafDefinitionItemWriter instanceof NafJdbcDefinitionItemWriterImpl) {

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
            NAF_DEFINITION
        );
    }

    @Nullable
    @Override
    protected String getDefaultJdbcDataSourceName() {
        return NAF_DEFAULT_JDBC_DATA_SOURCE_NAME;
    }

    @Nullable
    @Override
    protected String getMigrationFolder() {
        return NAF_MIGRATION_FOLDER;
    }

    @Nonnull
    @Override
    public ItemReader<NafDefinitionContainer> getItemReader() {
        return nafDefinitionItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<NafDefinitionContainer, NafDefinition> getItemProcessor() {
        return nafDefinitionItemProcessor;
    }

    @Nonnull
    @Override
    protected ItemWriter<NafDefinition> getItemWriter() {
        return nafDefinitionItemWriter;
    }
}
