/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_index;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.beans.CacheVFSEnabledConditional;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dademo
 */
@Slf4j
@Repository
@ConditionalOnBean(CacheVFSEnabledConditional.class)
class VFSCacheIndexRepositoryImpl<T extends InputStreamIdentifier<?>> extends VFSCacheIndexRepositoryBase<T> implements CacheIndexRepository<T> {

    public static final String DIRECTORY_ROOT_URI_SCHEME = "file";

    @Autowired
    private ObjectMapper mapper;

    @Override
    @SneakyThrows
    public List<CachedInputStreamIdentifier<T>> readIndex() {

        log.debug("Reading index file");
        try (final var lock = acquireLock()) {
            try (final var indexFile = indexFilePathUsingCacheDirectoryRoot()) {

                if (indexFile.getContent().getSize() > 0L) {
                    try {
                        return mapper.readValue(
                            indexFile.getContent().getInputStream(),
                            collectionTypeDefinitionOfCachedInputStreamIdentifierDef()
                        );
                    } catch (JacksonException ex) {
                        log.warn("Unable to read index file");
                        log.warn("Will clean the cache directory");
                        cleanCacheResourceDirectory();
                        return new ArrayList<>();
                    }
                } else {
                    return new ArrayList<>();
                }
            }
        }
    }

    @Override
    @SneakyThrows
    public void persistIndex(List<CachedInputStreamIdentifier<T>> indexContent) {

        log.debug("Writing index file");
        try (final var lock = acquireLock()) {
            try (final var indexFileObject = indexFilePathUsingCacheDirectoryRoot()) {
                mapper.writeValue(indexFileObject.getContent().getOutputStream(), indexContent);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @SneakyThrows
    private FileObject indexFilePathUsingCacheDirectoryRoot() {

        final var indexFile = getFileSystemManager()
            .resolveFile(URI.create(String.format(
                    "%s/%s",
                    getCacheVFSConfiguration().getCacheResourcesPath(),
                    LOCK_FILE_NAME
                ))
            );
        // We ensure the file exists
        indexFile.createFile();
        return indexFile;
    }


    private CollectionType collectionTypeDefinitionOfCachedInputStreamIdentifierDef() {

        return mapper.getTypeFactory()
            .constructCollectionType(List.class, CachedInputStreamIdentifier.class);
    }
}
