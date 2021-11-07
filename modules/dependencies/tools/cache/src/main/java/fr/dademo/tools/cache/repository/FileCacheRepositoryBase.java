package fr.dademo.tools.cache.repository;

import fr.dademo.tools.cache.repository.exception.NotADirectoryException;
import fr.dademo.tools.stream_definitions.configuration.CacheConfiguration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public abstract class FileCacheRepositoryBase implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCacheRepositoryBase.class);

    @Autowired
    @Getter
    private CacheConfiguration cacheConfiguration;

    @Override
    public void afterPropertiesSet() {

        // Initial checks
        ensureCacheDirectoryResourcesExists();
    }

    private void ensureCacheDirectoryResourcesExists() {

        final var cacheRoot = cacheConfiguration.getDirectoryRootPath().toFile();
        if (!cacheRoot.exists()) {
            LOGGER.info("Creating cache directory at path {}", cacheRoot.getAbsolutePath());
            final var created = cacheRoot.mkdirs();
            LOGGER.info("Directory {}{} created", cacheRoot.getAbsolutePath(), created ? "" : " not");
        } else {
            if (!cacheRoot.isDirectory()) {
                throw new NotADirectoryException(cacheRoot.getAbsolutePath());
            }
        }
    }
}
