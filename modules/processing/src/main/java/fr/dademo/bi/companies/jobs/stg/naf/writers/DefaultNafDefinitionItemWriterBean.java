package fr.dademo.bi.companies.jobs.stg.naf.writers;

import fr.dademo.bi.companies.jobs.stg.naf.NafDefinitionItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultNafDefinitionItemWriterBean {

    @Bean
    @ConditionalOnMissingBean(NafDefinitionItemWriter.class)
    public NafDefinitionItemWriter defaultNafDefinitionItemWriterBean() {
        return new NafDefinitionNoActionItemWriterImpl();
    }
}
