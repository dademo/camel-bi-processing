package fr.dademo.bi.companies.jobs.stg.company_legal.writers;

import fr.dademo.bi.companies.jobs.stg.company_legal.CompanyLegalItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegal;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_legal.JobDefinition.COMPANY_LEGAL_CONFIG_JOB_NAME;

@Component
@ConditionalOnProperty(
        value = CONFIG_JOBS_BASE + "." + COMPANY_LEGAL_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
        havingValue = CONFIG_NO_ACTION_TYPE
)
public class CompanyLegalNoActionItemWriterImpl extends NoActionItemWriter<CompanyLegal> implements CompanyLegalItemWriter {
}
