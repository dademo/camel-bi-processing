package fr.dademo.bi.companies.jobs.stg.company_legal.writers;

import fr.dademo.bi.companies.jobs.stg.company_legal.CompanyLegalWriter;
import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegal;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_legal.JobDefinition.COMPANY_LEGAL_CONFIG_JOB_NAME;

@Component
@Default
@ConditionalOnProperty(
        value = CONFIG_JOBS_BASE + "." + COMPANY_LEGAL_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
        havingValue = CONFIG_NO_ACTION_TYPE
)
public class NoActionCompanyLegalWriterImpl extends NoActionItemWriter<CompanyLegal> implements CompanyLegalWriter {
}
