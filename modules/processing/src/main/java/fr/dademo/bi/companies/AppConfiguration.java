package fr.dademo.bi.companies;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "services", namingStrategy = ConfigMapping.NamingStrategy.KEBAB_CASE)
public interface AppConfiguration {
}
