package fr.dademo.bi.companies.beans;

import fr.dademo.bi.companies.repositories.CacheHandlerProvider;
import fr.dademo.bi.companies.repositories.CachedHttpDataQuerierImpl;
import fr.dademo.bi.companies.repositories.HttpDataQuerier;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachedHttpDataQuerier {

    @Bean
    @ConditionalOnMissingBean(HttpDataQuerier.class)
    public CachedHttpDataQuerierImpl defaultCustomizedCachedHttpDataQuerier(OkHttpClient okHttpClient,
                                                                            CacheHandlerProvider cacheHandlerProvider) {
        return new CachedHttpDataQuerierImpl(okHttpClient, cacheHandlerProvider);
    }

    @Bean
    @ConditionalOnMissingBean({
            CacheHandlerProvider.class,
            CachedHttpDataQuerierImpl.class,
    })
    public CachedHttpDataQuerierImpl defaultCachedHttpDataQuerier(OkHttpClient okHttpClient) {
        return new CachedHttpDataQuerierImpl(okHttpClient);
    }
}
