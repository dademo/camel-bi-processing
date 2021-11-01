package fr.dademo.bi.companies.jobs.stg.association.writers;

import fr.dademo.bi.companies.jobs.stg.association.AssociationItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultAssociationItemWriterBean {

    @Bean
    @ConditionalOnMissingBean(AssociationItemWriter.class)
    public AssociationItemWriter defaultAssociationItemWriterBean() {
        return new AssociationNoActionItemWriterImpl();
    }
}
