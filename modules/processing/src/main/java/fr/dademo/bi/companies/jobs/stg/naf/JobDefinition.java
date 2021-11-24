/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.configuration.BatchConfiguration;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@Component(JobDefinition.NAF_JOB_NAME)
public class JobDefinition extends BaseChunkJob<NafDefinitionContainer, NafDefinition> {

    public static final String NAF_CONFIG_JOB_NAME = "naf";
    public static final String NAF_NORMALIZED_CONFIG_JOB_NAME = "naf";
    public static final String NAF_JOB_NAME = "stg_" + NAF_NORMALIZED_CONFIG_JOB_NAME;

    @Autowired
    private BatchConfiguration batchConfiguration;

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
