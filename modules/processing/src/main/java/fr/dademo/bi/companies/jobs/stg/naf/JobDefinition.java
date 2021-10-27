package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.configuration.JobsConfiguration;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class JobDefinition extends BaseChunkJob<NafDefinitionContainer, NafDefinition> {

    private static final String CONFIG_JOB_NAME = "naf";
    public static final String NAF_JOB_NAME = "stg_" + CONFIG_JOB_NAME;

    @Autowired
    private JobsConfiguration jobsConfiguration;

    @Autowired
    private NafReader nafReader;
    @Autowired
    private NafProcessor nafProcessor;
    @Autowired
    private NafJdbcWriter nafJdbcWriter;

    @Nonnull
    protected JobsConfiguration.JobConfiguration getJobConfiguration() {
        return jobsConfiguration.getJobConfigurationByName(CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return NAF_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<NafDefinitionContainer> getItemReader() {
        return nafReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<NafDefinitionContainer, NafDefinition> getItemProcessor() {
        return nafProcessor;
    }

    @Nonnull
    @Override
    protected ItemWriter<NafDefinition> getItemWriter() {
        return nafJdbcWriter;
    }
}
