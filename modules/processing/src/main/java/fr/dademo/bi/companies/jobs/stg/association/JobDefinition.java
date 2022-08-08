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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@Component(JobDefinition.ASSOCIATION_JOB_NAME)
public class JobDefinition extends BaseChunkJob<WrappedRowResource, Association> {

    public static final String ASSOCIATION_CONFIG_JOB_NAME = "association";
    public static final String ASSOCIATION_NORMALIZED_CONFIG_JOB_NAME = "association";
    public static final String ASSOCIATION_JOB_NAME = "stg_" + ASSOCIATION_NORMALIZED_CONFIG_JOB_NAME;

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
