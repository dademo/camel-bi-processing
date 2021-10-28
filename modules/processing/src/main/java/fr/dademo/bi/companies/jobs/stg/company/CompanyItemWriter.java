package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
import org.springframework.batch.item.ItemWriter;

public interface CompanyItemWriter extends ItemWriter<Company> {
}
