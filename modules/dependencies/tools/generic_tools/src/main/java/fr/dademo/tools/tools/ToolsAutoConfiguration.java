package fr.dademo.tools.tools;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackageClasses = ToolsAutoConfiguration.class)
public class ToolsAutoConfiguration {
}
