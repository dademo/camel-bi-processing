package fr.dademo.bi.companies.jobs.stg.company_legal.writers;

import fr.dademo.bi.companies.jobs.stg.company_legal.CompanyLegalItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultCompanyLegalItemWriterBean {

    @Bean
    @ConditionalOnMissingBean(CompanyLegalItemWriter.class)
    public CompanyLegalItemWriter defaultCompanyLegalItemWriterBean() {
        return new CompanyLegalNoActionItemWriterImpl();
    }
}
