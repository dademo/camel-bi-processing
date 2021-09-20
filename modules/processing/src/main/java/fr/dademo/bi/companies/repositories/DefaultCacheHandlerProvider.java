package fr.dademo.bi.companies.repositories;

import io.quarkus.arc.DefaultBean;
import lombok.Getter;
import org.apache.commons.lang3.SystemUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.Nullable;
import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.nio.file.Path;

@ApplicationScoped
@DefaultBean
public class DefaultCacheHandlerProvider implements CacheHandlerProvider {

    public static final String DEFAULT_CACHE_DIRECTORY_ROOT = String.join(File.separator, SystemUtils.getUserHome().getAbsolutePath(), ".cache", "quarkus-http");
    public static final String DEFAULT_CACHE_DIRECTORY_ROOT_PARAM = "default";

    @Getter
    @ConfigProperty(name = "http.cache.enabled", defaultValue = "true")
    Boolean httpCacheEnabled;

    @ConfigProperty(name = "http.cache.directoryRoot", defaultValue = DEFAULT_CACHE_DIRECTORY_ROOT_PARAM)
    String cacheDirectoryRoot;


    @Nullable
    @Override
    public CacheHandler getCacheHandler() {

        if (getHttpCacheEnabled()) {
            return new CacheHandlerImpl(getCacheDirectoryRoot());
        } else {
            return null;
        }
    }

    private boolean hasConfiguredCacheDirectoryRoot() {

        return cacheDirectoryRoot.isBlank() ||
                DEFAULT_CACHE_DIRECTORY_ROOT_PARAM.equals(cacheDirectoryRoot);
    }

    private Path getCacheDirectoryRoot() {

        return Path.of(hasConfiguredCacheDirectoryRoot() ?
                DEFAULT_CACHE_DIRECTORY_ROOT :
                cacheDirectoryRoot
        );
    }
}
