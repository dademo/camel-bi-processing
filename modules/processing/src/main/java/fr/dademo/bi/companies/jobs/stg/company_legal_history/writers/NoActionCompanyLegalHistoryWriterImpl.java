package fr.dademo.bi.companies.jobs.stg.company_legal_history.writers;

import fr.dademo.bi.companies.jobs.stg.company_legal_history.CompanyLegalHistoryWriter;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_legal_history.JobDefinition.COMPANY_LEGAL_HISTORY_CONFIG_JOB_NAME;

@Component
@Default
@ConditionalOnProperty(
        value = CONFIG_JOBS_BASE + "." + COMPANY_LEGAL_HISTORY_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
        havingValue = CONFIG_NO_ACTION_TYPE
)
public class NoActionCompanyLegalHistoryWriterImpl extends NoActionItemWriter<CompanyLegalHistory> implements CompanyLegalHistoryWriter {
}
