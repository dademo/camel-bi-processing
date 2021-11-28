/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_history;

import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@Component(JobDefinition.COMPANY_HISTORY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, CompanyHistory> {

    public static final String COMPANY_HISTORY_CONFIG_JOB_NAME = "company-history";
    public static final String COMPANY_HISTORY_NORMALIZED_CONFIG_JOB_NAME = "company_history";
    public static final String COMPANY_HISTORY_JOB_NAME = "stg_" + COMPANY_HISTORY_NORMALIZED_CONFIG_JOB_NAME;

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private CompanyHistoryItemReader companyHistoryItemReader;
    @Autowired
    private CompanyHistoryItemMapper companyHistoryItemMapper;
    @Autowired
    private CompanyHistoryItemWriter companyHistoryItemWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(COMPANY_HISTORY_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_HISTORY_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return companyHistoryItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, CompanyHistory> getItemProcessor() {
        return companyHistoryItemMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<CompanyHistory> getItemWriter() {
        return companyHistoryItemWriter;
    }
}
