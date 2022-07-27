/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_index;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.configuration.CacheVFSConfiguration;
import fr.dademo.tools.cache.repository.exception.NotADirectoryException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author dademo
 */
@Slf4j
@NoArgsConstructor
public abstract class VFSCacheIndexRepositoryBase<T extends InputStreamIdentifier<?>> extends BaseCacheIndexRepository<T> implements InitializingBean {

    public static final String DIRECTORY_ROOT_URI_SCHEME = "file";
    protected static final String LOCK_FILE_NAME = "index.lock";


    @Getter
    @Autowired
    private CacheVFSConfiguration cacheVFSConfiguration;
    @Getter
    @Autowired
    private FileSystemManager fileSystemManager;

    @Override
    public void afterPropertiesSet() {

        // Creating the cache directory
        ensureCacheDirectoryResourcesExists();
    }

    protected void ensureCacheDirectoryResourcesExists() {

        // Creating the temp directory
        final var cacheResourcesPath = getCacheResourcePath();

        try {
            if (cacheResourcesPath.exists() && !cacheResourcesPath.isFolder()) {
                throw new NotADirectoryException(cacheResourcesPath.getURI());
            } else {
                cacheResourcesPath.createFolder();
                log.info("Created cache folder {}", cacheResourcesPath.getURI());
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    protected void cleanCacheResourceDirectory() throws IOException {

        final var deleted = getCacheResourcePath().deleteAll();
        if (deleted > 0) {
            log.info("Removed cache directory {} and all its descendants", getCacheResourcePath().getURI());
        }
        // We roll back to a consistent state
        ensureCacheDirectoryResourcesExists();
    }

    @SneakyThrows
    private FileObject getCacheResourcePath() {
        return fileSystemManager.resolveFile(cacheVFSConfiguration.getCacheResourcesPath());
    }
}
