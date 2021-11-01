package fr.dademo.bi.companies.jobs.stg.company_history.writers;

import fr.dademo.bi.companies.jobs.stg.company_history.CompanyHistoryItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultCompanyHistoryItemWriterBean {

    @Bean
    @ConditionalOnMissingBean(CompanyHistoryItemWriter.class)
    public CompanyHistoryItemWriter defaultCompanyHistoryItemWriterBean() {
        return new CompanyHistoryNoActionItemWriterImpl();
    }
}
