/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_index;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.configuration.CacheConfiguration;
import fr.dademo.tools.cache.repository.exception.NotADirectoryException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

/**
 * @author dademo
 */
@Slf4j
@NoArgsConstructor
public abstract class FileCacheIndexRepositoryBase<T extends InputStreamIdentifier<?>> extends BaseCacheIndexRepository<T> implements InitializingBean {

    public static final String DIRECTORY_ROOT_URI_SCHEME = "file";
    protected static final String LOCK_FILE_NAME = "index.lock";
    protected static final String RESOURCES_DIRECTORY_NAME = "resources";

    @Autowired
    @Getter
    private CacheConfiguration cacheConfiguration;

    @Override
    public void afterPropertiesSet() {
        ensureCacheDirectoryResourcesExists();
    }

    protected void ensureCacheDirectoryResourcesExists() {

        final var cacheRoot = new File(cacheConfiguration.getFile().getDirectoryRootUri(DIRECTORY_ROOT_URI_SCHEME));
        final var cacheResources = new File(URI.create(String.format(
            "%s/%s",
            cacheConfiguration.getFile().getDirectoryRootUri(DIRECTORY_ROOT_URI_SCHEME),
            RESOURCES_DIRECTORY_NAME
        )));

        if (!cacheRoot.exists()) {
            log.info("Creating cache directory at path {}", cacheRoot.getAbsolutePath());
            final var created = cacheRoot.mkdirs();
            log.info("Directory {}{} created", cacheRoot.getAbsolutePath(), created ? "" : " not");
        } else {
            if (!cacheRoot.isDirectory()) {
                throw new NotADirectoryException(cacheRoot.getAbsolutePath());
            }
        }
        if (!cacheResources.exists()) {
            log.info("Creating cache resources directory at path {}", cacheResources.getAbsolutePath());
            final var created = cacheResources.mkdirs();
            log.info("Directory {}{} created", cacheResources.getAbsolutePath(), created ? "" : " not");
        } else {
            if (!cacheResources.isDirectory()) {
                throw new NotADirectoryException(cacheResources.getAbsolutePath());
            }
        }
    }

    protected void cleanCacheResourceDirectory() throws IOException {

        for (final var file : Optional.ofNullable(new File(cacheConfiguration.getFile().getDirectoryRootUri(DIRECTORY_ROOT_URI_SCHEME)).listFiles()).orElse(new File[0])) {
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
