package fr.dademo.bi.companies.jobs.stg.company_history.writers;

import fr.dademo.bi.companies.jobs.stg.company_history.CompanyHistoryItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

@Component
@Default
public class NoActionCompanyHistoryItemWriterImpl extends NoActionItemWriter<CompanyHistory> implements CompanyHistoryItemWriter {
}
