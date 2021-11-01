package fr.dademo.bi.companies.jobs.stg.company_inheritance.writers;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.CompanyInheritanceItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultCompanyInheritanceItemWriterBean {

    @Bean
    @ConditionalOnMissingBean(CompanyInheritanceItemWriter.class)
    public CompanyInheritanceItemWriter defaultCompanyInheritanceItemWriterBean() {
        return new CompanyInheritanceNoActionItemWriterImpl();
    }
}
