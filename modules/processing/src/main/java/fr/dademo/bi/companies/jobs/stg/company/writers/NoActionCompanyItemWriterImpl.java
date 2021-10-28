package fr.dademo.bi.companies.jobs.stg.company.writers;

import fr.dademo.bi.companies.jobs.stg.company.CompanyItemWriter;
import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

@Component
@Default
public class NoActionCompanyItemWriterImpl extends NoActionItemWriter<Company> implements CompanyItemWriter {
}
