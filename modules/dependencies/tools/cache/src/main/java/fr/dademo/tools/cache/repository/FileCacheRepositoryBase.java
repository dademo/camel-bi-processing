/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository;

import fr.dademo.data.generic.stream_definitions.configuration.CacheConfiguration;
import fr.dademo.tools.cache.repository.exception.NotADirectoryException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author dademo
 */
@NoArgsConstructor
public abstract class FileCacheRepositoryBase implements InitializingBean {

    protected static final String RESOURCES_DIRECTORY_NAME = "resources";
    protected static final String LOCK_FILE_NAME = "index.lock";
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
        final var cacheResources = cacheConfiguration.getDirectoryRootPath().resolve(RESOURCES_DIRECTORY_NAME).toFile();

        if (!cacheRoot.exists()) {
            LOGGER.info("Creating cache directory at path {}", cacheRoot.getAbsolutePath());
            final var created = cacheRoot.mkdirs();
            LOGGER.info("Directory {}{} created", cacheRoot.getAbsolutePath(), created ? "" : " not");
        } else {
            if (!cacheRoot.isDirectory()) {
                throw new NotADirectoryException(cacheRoot.getAbsolutePath());
            }
        }
        if (!cacheResources.exists()) {
            LOGGER.info("Creating cache resources directory at path {}", cacheResources.getAbsolutePath());
            final var created = cacheResources.mkdirs();
            LOGGER.info("Directory {}{} created", cacheResources.getAbsolutePath(), created ? "" : " not");
        } else {
            if (!cacheResources.isDirectory()) {
                throw new NotADirectoryException(cacheResources.getAbsolutePath());
            }
        }
    }

    protected void cleanCacheResourceDirectory() throws IOException {

        for (final var file : Optional.ofNullable(cacheConfiguration.getDirectoryRootPath().toFile().listFiles()).orElse(new File[0])) {
            if (file.isDirectory()) {
                FileUtils.deleteDirectory(file);
            } else {
                FileUtils.delete(file);
            }
        }
        // We roll back to a consistent state
        ensureCacheDirectoryResourcesExists();
    }
}
