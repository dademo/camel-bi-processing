package fr.dademo.reader.http_cache;

import fr.dademo.reader.http.HttpAutoConfiguration;
import fr.dademo.reader.http.repository.HttpDataQuerierRepository;
import fr.dademo.tools.cache.CacheAutoConfiguration;
import fr.dademo.tools.cache.repository.CacheRepository;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({HttpDataQuerierRepository.class, CacheRepository.class})
@AutoConfigureBefore(HttpAutoConfiguration.class)
@AutoConfigureAfter(CacheAutoConfiguration.class)
@ComponentScan(basePackageClasses = HttpCacheAutoConfiguration.class)
public class HttpCacheAutoConfiguration {
}
