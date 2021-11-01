package fr.dademo.bi.companies.repositories;

import fr.dademo.bi.companies.configuration.HttpConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;

@Component
public class DefaultCacheHandlerProvider implements CacheHandlerProvider {

    @Autowired
    private HttpConfiguration httpConfiguration;

    @Nullable
    @Override
    public CacheHandler getCacheHandler() {

        return Optional.ofNullable(httpConfiguration)
                .map(HttpConfiguration::getCacheConfiguration)
                .filter(HttpConfiguration.CacheConfiguration::isEnabled)
                .map(HttpConfiguration.CacheConfiguration::getDirectoryRootPath)
                .map(CacheHandlerImpl::new)
                .orElse(null);
    }
}
