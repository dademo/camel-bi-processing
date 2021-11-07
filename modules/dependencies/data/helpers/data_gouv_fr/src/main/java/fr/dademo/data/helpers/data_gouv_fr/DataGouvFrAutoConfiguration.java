package fr.dademo.data.helpers.data_gouv_fr;

import fr.dademo.reader.http.HttpAutoConfiguration;
import fr.dademo.reader.http.repository.HttpDataQuerierRepository;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(HttpDataQuerierRepository.class)
@AutoConfigureAfter(HttpAutoConfiguration.class)
@ComponentScan(basePackageClasses = DataGouvFrAutoConfiguration.class)
public class DataGouvFrAutoConfiguration {
}
