package fr.dademo.bi.companies.jobs.stg.company_legal_history.writers;

import fr.dademo.bi.companies.jobs.stg.company_legal_history.CompanyLegalHistoryItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultCompanyLegalHistoryItemWriterBean {

    @Bean
    @ConditionalOnMissingBean(CompanyLegalHistoryItemWriter.class)
    public CompanyLegalHistoryItemWriter defaultCompanyLegalHistoryItemWriterBean() {
        return new CompanyLegalHistoryNoActionItemWriterImpl();
    }
}
