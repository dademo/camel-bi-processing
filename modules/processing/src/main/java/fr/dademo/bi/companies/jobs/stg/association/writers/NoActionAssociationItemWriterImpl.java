package fr.dademo.bi.companies.jobs.stg.association.writers;

import fr.dademo.bi.companies.jobs.stg.association.AssociationItemWriter;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.association.JobDefinition.ASSOCIATION_CONFIG_JOB_NAME;

@Component
@Default
@ConditionalOnProperty(
        value = CONFIG_JOBS_BASE + "." + ASSOCIATION_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
        havingValue = CONFIG_NO_ACTION_TYPE
)
public class NoActionAssociationItemWriterImpl extends NoActionItemWriter<Association> implements AssociationItemWriter {
}
