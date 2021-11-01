package fr.dademo.bi.companies.jobs.stg.company.writers;

import fr.dademo.bi.companies.jobs.stg.company.CompanyItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultCompanyItemWriterBean {

    @Bean
    @ConditionalOnMissingBean(CompanyItemWriter.class)
    public CompanyItemWriter defaultCompanyItemWriterBean() {
        return new CompanyNoActionItemWriterImpl();
    }
}
