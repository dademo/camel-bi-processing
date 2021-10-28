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

@Component(JobDefinition.NAF_JOB_NAME)
public class JobDefinition extends BaseChunkJob<NafDefinitionContainer, NafDefinition> {

    private static final String CONFIG_JOB_NAME = "naf";
    private static final String NORMALIZED_CONFIG_JOB_NAME = "naf";
    public static final String NAF_JOB_NAME = "stg_" + NORMALIZED_CONFIG_JOB_NAME;

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private NafDefinitionReader nafDefinitionReader;
    @Autowired
    private NafDefinitionProcessor nafDefinitionProcessor;
    @Autowired
    private NafDefinitionWriter nafDefinitionWriter;

    @Nonnull
    protected BatchConfiguration.JobConfiguration getJobConfiguration() {
        return batchConfiguration.getJobConfigurationByName(CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return NAF_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<NafDefinitionContainer> getItemReader() {
        return nafDefinitionReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<NafDefinitionContainer, NafDefinition> getItemProcessor() {
        return nafDefinitionProcessor;
    }

    @Nonnull
    @Override
    protected ItemWriter<NafDefinition> getItemWriter() {
        return nafDefinitionWriter;
    }
}
