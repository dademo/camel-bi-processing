package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.bi.companies.configuration.BatchConfiguration;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component(JobDefinition.ASSOCIATION_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, Association> {

    private static final String CONFIG_JOB_NAME = "association";
    private static final String NORMALIZED_CONFIG_JOB_NAME = "association";
    public static final String ASSOCIATION_JOB_NAME = "stg_" + NORMALIZED_CONFIG_JOB_NAME;

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private AssociationReader associationReader;
    @Autowired
    private AssociationMapper associationMapper;
    @Autowired
    private AssociationItemWriter associationItemWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return ASSOCIATION_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return associationReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, Association> getItemProcessor() {
        return associationMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<Association> getItemWriter() {
        return associationItemWriter;
    }
}
