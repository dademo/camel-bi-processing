/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.configuration.CacheVFSConfiguration;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.cache.repository.exception.NotADirectoryException;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.Base64;
import java.util.Random;

@Slf4j
@Getter
public abstract class VFSCacheRepositoryBeanLifecycle<T extends InputStreamIdentifier<?>> extends BaseCacheRepository<T> implements InitializingBean, DisposableBean {

    public static final int TEMP_DIRECTORY_RANDOM_LENGTH = 16;
    public static final int TEMP_FILE_RANDOM_LENGTH = 32;
    protected static final String RESOURCES_DIRECTORY_NAME = "resources";
    private static final String TEMP_PREFIX = normalizedName(VFSCacheRepositoryImpl.class.getName());
    private static final Random random = new Random();

    private URI tempDirectoryURI = null;

    @Autowired
    private CacheVFSConfiguration cacheVFSConfiguration;

    @Autowired
    private FileSystemManager fileSystemManager;


    private static String normalizedName(String string) {
        return string.replace(File.separator, "_");
    }

    @Override
    public void afterPropertiesSet() {

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

    @Override
    public void destroy() throws Exception {

        // Removing the temporary folder
        final var deleted = getTempDirectoryResourcePath().deleteAll();
        if (deleted > 0) {
            log.info("Removed cache directory {} and all its descendants", getTempDirectoryResourcePath().getURI());
        }
    }

    protected FileObject getTempFile() {
        return getTempFile("");
    }

    @SneakyThrows
    protected FileObject getTempFile(@Nonnull String baseName) {
        final var file = getTempDirectoryResourcePath()
            .resolveFile(
                ((baseName.length() > 0) ? "-" + baseName : "") +
                    getBase64Random(TEMP_FILE_RANDOM_LENGTH)
            );
        // We ensure the file exists
        file.createFile();
        return file;
    }

    @SneakyThrows
    protected FileObject getCachedFileObjectFor(CachedInputStreamIdentifier<?> cachedInputStreamIdentifier) {

        final var file = fileSystemManager.resolveFile(URI.create(
            cacheVFSConfiguration.getCacheResourcesPath() +
                RESOURCES_DIRECTORY_NAME + "/" +
                cachedInputStreamIdentifier.getFileName()
        ));
        // We ensure the file exists
        file.createFile();
        return file;
    }

    @SneakyThrows
    private synchronized FileObject getTempDirectoryResourcePath() {

        if (tempDirectoryURI == null) {
            final var tempResourcesPath = cacheVFSConfiguration.getTempResourcesPath();

            if (cacheVFSConfiguration.isRandomTempPath()) {
                tempDirectoryURI = URI.create(tempResourcesPath + getBase64Random(TEMP_DIRECTORY_RANDOM_LENGTH));
            } else {
                tempDirectoryURI = tempResourcesPath;
            }
        }

        return fileSystemManager.resolveFile(tempDirectoryURI);
    }

    @SneakyThrows
    private FileObject getCacheResourcePath() {

        final var cacheVFSURI = URI.create(cacheVFSConfiguration.getCacheResourcesPath() + RESOURCES_DIRECTORY_NAME);
        return fileSystemManager.resolveFile(cacheVFSURI);
    }

    private String getBase64Random(int length) {

        final var bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().encodeToString(bytes);
    }
}
