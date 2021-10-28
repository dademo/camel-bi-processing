package fr.dademo.bi.companies.jobs.stg.company_history;

import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import org.springframework.batch.item.ItemWriter;

public interface CompanyHistoryItemWriter extends ItemWriter<CompanyHistory> {
}
