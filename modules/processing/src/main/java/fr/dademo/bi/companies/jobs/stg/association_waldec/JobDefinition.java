package fr.dademo.bi.companies.jobs.stg.association_waldec;

import fr.dademo.bi.companies.configuration.JobsConfiguration;
import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec;
import fr.dademo.bi.companies.tools.batch.job.BaseChunkJob;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
@Qualifier(JobDefinition.ASSOCIATION_WALDEC_JOB_NAME)
public class JobDefinition extends BaseChunkJob<CSVRecord, AssociationWaldec> {

    private static final String CONFIG_JOB_NAME = "association_waldec";
    public static final String ASSOCIATION_WALDEC_JOB_NAME = "stg_" + CONFIG_JOB_NAME;

    @Autowired
    private JobsConfiguration jobsConfiguration;

    @Autowired
    private AssociationWaldecReader associationWaldecReader;
    @Autowired
    private AssociationWaldecMapper associationWaldecMapper;
    @Autowired
    private AssociationWaldecJdbcWriter associationWaldecJdbcWriter;

    @Nonnull
    protected JobsConfiguration.JobConfiguration getJobConfiguration() {
        return jobsConfiguration.getJobConfigurationByName(CONFIG_JOB_NAME);
    }

    @Nonnull
    @Override
    public String getJobName() {
        return ASSOCIATION_WALDEC_JOB_NAME;
    }

    @Nonnull
    @Override
    public ItemReader<CSVRecord> getItemReader() {
        return associationWaldecReader;
    }

    @Nonnull
    @Override
    public ItemProcessor<CSVRecord, AssociationWaldec> getItemProcessor() {
        return associationWaldecMapper;
    }

    @Nonnull
    @Override
    protected ItemWriter<AssociationWaldec> getItemWriter() {
        return associationWaldecJdbcWriter;
    }
}
