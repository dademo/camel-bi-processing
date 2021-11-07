package fr.dademo.tools.cache;

import fr.dademo.data.generic.stream_definitions.configuration.CacheConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheConfiguration.class)
@ComponentScan(basePackageClasses = {CacheAutoConfiguration.class, CacheConfiguration.class})
public class CacheAutoConfiguration {
}
