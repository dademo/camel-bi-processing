/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association_waldec;

import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.tools.batch.job.BaseChunkJob;
import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec;
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
@Component(JobDefinition.ASSOCIATION_WALDEC_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, AssociationWaldec> {

    public static final String ASSOCIATION_WALDEC_CONFIG_JOB_NAME = "association-waldec";
    public static final String ASSOCIATION_WALDEC_NORMALIZED_CONFIG_JOB_NAME = "association_waldec";
    public static final String ASSOCIATION_WALDEC_JOB_NAME = "stg_" + ASSOCIATION_WALDEC_NORMALIZED_CONFIG_JOB_NAME;

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private AssociationWaldecItemReader associationWaldecItemReader;
    @Autowired
    private AssociationWaldecItemMapper associationWaldecItemMapper;
    @Autowired
    private AssociationWaldecItemWriter associationWaldecItemWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(ASSOCIATION_WALDEC_CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return ASSOCIATION_WALDEC_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return associationWaldecItemReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, AssociationWaldec> getItemProcessor() {
        return associationWaldecItemMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<AssociationWaldec> getItemWriter() {
        return associationWaldecItemWriter;
    }
}
