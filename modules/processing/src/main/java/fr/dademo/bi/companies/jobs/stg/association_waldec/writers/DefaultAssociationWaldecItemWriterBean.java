package fr.dademo.bi.companies.jobs.stg.association_waldec.writers;

import fr.dademo.bi.companies.jobs.stg.association_waldec.AssociationWaldecItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultAssociationWaldecItemWriterBean {

    @Bean
    @ConditionalOnMissingBean(AssociationWaldecItemWriter.class)
    public AssociationWaldecItemWriter defaultAssociationWaldecItemWriterBean() {
        return new AssociationWaldecNoActionItemWriterImpl();
    }
}
