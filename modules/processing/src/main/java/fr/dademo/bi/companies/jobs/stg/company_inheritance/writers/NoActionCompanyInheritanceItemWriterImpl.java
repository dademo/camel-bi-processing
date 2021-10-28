package fr.dademo.bi.companies.jobs.stg.company_inheritance.writers;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.CompanyInheritanceItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

@Component
@Default
public class NoActionCompanyInheritanceItemWriterImpl extends NoActionItemWriter<CompanyInheritance> implements CompanyInheritanceItemWriter {
}
