/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.bi.companies.configuration.BatchConfiguration;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
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
@Component(JobDefinition.COMPANY_INHERITANCE_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, CompanyInheritance> {

    public static final String COMPANY_INHERITANCE_CONFIG_JOB_NAME = "company-inheritance";
    public static final String COMPANY_INHERITANCE_NORMALIZED_CONFIG_JOB_NAME = "company_inheritance";
    public static final String COMPANY_INHERITANCE_JOB_NAME = "stg_" + COMPANY_INHERITANCE_NORMALIZED_CONFIG_JOB_NAME;


    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private CompanyInheritanceItemReader companyInheritanceItemReader;
    @Autowired
    private CompanyInheritanceItemMapper companyInheritanceItemMapper;
    @Autowired
    private CompanyInheritanceItemWriter companyInheritanceItemWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(COMPANY_INHERITANCE_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return COMPANY_INHERITANCE_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return companyInheritanceItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, CompanyInheritance> getItemProcessor() {
        return companyInheritanceItemMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<CompanyInheritance> getItemWriter() {
        return companyInheritanceItemWriter;
    }
}
