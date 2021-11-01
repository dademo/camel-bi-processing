package fr.dademo.bi.companies.jobs.stg.company_history.writers;

import fr.dademo.bi.companies.jobs.stg.company_history.CompanyHistoryItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_history.JobDefinition.COMPANY_HISTORY_CONFIG_JOB_NAME;

@Component
@ConditionalOnProperty(
        value = CONFIG_JOBS_BASE + "." + COMPANY_HISTORY_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
        havingValue = CONFIG_NO_ACTION_TYPE
)
public class CompanyHistoryNoActionItemWriterImpl extends NoActionItemWriter<CompanyHistory> implements CompanyHistoryItemWriter {
}
