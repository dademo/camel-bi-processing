package fr.dademo.bi.companies.jobs.stg.company_legal_history.writers;

import fr.dademo.bi.companies.jobs.stg.company_legal_history.CompanyLegalHistoryWriter;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

@Component
@Default
public class NoActionCompanyLegalHistoryWriterImpl extends NoActionItemWriter<CompanyLegalHistory> implements CompanyLegalHistoryWriter {
}
