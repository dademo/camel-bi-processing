package fr.dademo.data.helpers.data_gouv_fr;

import fr.dademo.data.helpers.data_gouv_fr.cache.flow_ignore_checkers.DataGouvFrCacheFlowIgnoreChecker;
import fr.dademo.tools.cache.validators.CacheFlowIgnoreChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultDataGouvFrBeans {

    @Bean
    public CacheFlowIgnoreChecker<?> dataGouvFrCacheFlowIgnoreChecker() {
        return new DataGouvFrCacheFlowIgnoreChecker();
    }
}
