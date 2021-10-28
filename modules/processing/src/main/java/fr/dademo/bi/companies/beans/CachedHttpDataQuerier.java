package fr.dademo.bi.companies.beans;

import fr.dademo.bi.companies.repositories.CacheHandlerProvider;
import fr.dademo.bi.companies.repositories.CachedHttpDataQuerierImpl;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Default;

@Configuration
public class CachedHttpDataQuerier {

    @Bean
    @Default
    public CachedHttpDataQuerierImpl defaultCustomizedCachedHttpDataQuerier(@Autowired OkHttpClient okHttpClient,
                                                                            @Autowired CacheHandlerProvider cacheHandlerProvider) {
        return new CachedHttpDataQuerierImpl(okHttpClient, cacheHandlerProvider);
    }

    @Bean
    @ConditionalOnMissingBean({
            CacheHandlerProvider.class,
            CachedHttpDataQuerierImpl.class,
    })
    public CachedHttpDataQuerierImpl defaultCachedHttpDataQuerier(@Autowired OkHttpClient okHttpClient) {
        return new CachedHttpDataQuerierImpl(okHttpClient);
    }
}
