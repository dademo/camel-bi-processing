package fr.dademo.reader.http;

import fr.dademo.data.generic.stream_definitions.configuration.HttpConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HttpConfiguration.class)
@ComponentScan(basePackageClasses = {HttpAutoConfiguration.class, HttpConfiguration.class})
public class HttpAutoConfiguration {
}
