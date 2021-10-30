package fr.dademo.bi.companies.jobs.stg.company_inheritance.writers;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.CompanyInheritanceItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.JobDefinition.COMPANY_INHERITANCE_CONFIG_JOB_NAME;

@Component
@Default
@ConditionalOnProperty(
        value = CONFIG_JOBS_BASE + "." + COMPANY_INHERITANCE_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
        havingValue = CONFIG_NO_ACTION_TYPE
)
public class NoActionCompanyInheritanceItemWriterImpl extends NoActionItemWriter<CompanyInheritance> implements CompanyInheritanceItemWriter {
}
