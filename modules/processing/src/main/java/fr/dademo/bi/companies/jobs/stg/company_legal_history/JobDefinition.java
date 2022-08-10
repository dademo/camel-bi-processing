/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal_history;

import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.batch.tools.batch.job.JooqTruncateTasklet;
import fr.dademo.bi.companies.jobs.exceptions.MissingBeanException;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.writers.CompanyLegalHistoryJdbcItemWriterImpl;
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
import static fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistoryTable.COMPANY_LEGAL_HISTORY;

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

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Nullable
    @Autowired(required = false)
    @Qualifier(STG_DATA_SOURCE_DSL_CONTEXT_BEAN_NAME)
    private DSLContext dslContext;

    @Autowired
    private CompanyLegalHistoryItemReader companyLegalHistoryItemReader;
    @Autowired
    private CompanyLegalHistoryItemMapper companyLegalHistoryItemMapper;
    @Autowired
    private CompanyLegalHistoryItemWriter companyLegalHistoryItemWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(COMPANY_LEGAL_HISTORY_CONFIG_JOB_NAME);
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
            COMPANY_LEGAL_HISTORY
        );
    }

    @Nullable
    @Override
    protected String getDefaultJdbcDataSourceName() {
        return COMPANY_LEGAL_HISTORY_DEFAULT_JDBC_DATA_SOURCE_NAME;
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
