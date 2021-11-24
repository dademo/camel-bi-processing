/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.bi.companies.configuration.BatchConfiguration;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
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
@Component(JobDefinition.COMPANY_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, Company> {

    public static final String COMPANY_CONFIG_JOB_NAME = "company";
    public static final String COMPANY_NORMALIZED_CONFIG_JOB_NAME = "company";
    public static final String COMPANY_JOB_NAME = "stg_" + COMPANY_NORMALIZED_CONFIG_JOB_NAME;

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private CompanyItemReader companyItemReader;
    @Autowired
    private CompanyItemMapper companyItemMapper;
    @Autowired
    private CompanyItemWriter companyItemWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(COMPANY_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return companyItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, Company> getItemProcessor() {
        return companyItemMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<Company> getItemWriter() {
        return companyItemWriter;
    }
}
