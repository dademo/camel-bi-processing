package fr.dademo.bi.companies.jobs.stg.company_legal_history;

import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import org.springframework.batch.item.ItemWriter;

public interface CompanyLegalHistoryWriter extends ItemWriter<CompanyLegalHistory> {
}
