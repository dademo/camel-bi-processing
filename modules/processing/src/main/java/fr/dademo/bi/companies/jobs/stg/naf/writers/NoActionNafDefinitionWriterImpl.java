package fr.dademo.bi.companies.jobs.stg.naf.writers;

import fr.dademo.bi.companies.jobs.stg.naf.NafDefinitionWriter;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.naf.JobDefinition.NAF_CONFIG_JOB_NAME;

@Component
@Default
@ConditionalOnProperty(
        value = CONFIG_JOBS_BASE + "." + NAF_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
        havingValue = CONFIG_NO_ACTION_TYPE
)
public class NoActionNafDefinitionWriterImpl extends NoActionItemWriter<NafDefinition> implements NafDefinitionWriter {
}
