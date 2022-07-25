/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

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
import fr.dademo.data.generic.stream_definitions.configuration.CacheConfiguration;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.cache.repository.lock.FileCacheLockProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dademo
 */
@Slf4j
@Repository
class FileCacheIndexRepositoryImpl<T extends InputStreamIdentifier<?>> extends FileCacheIndexRepositoryBase<T> implements CacheIndexRepository<T> {

    public static final String DIRECTORY_ROOT_URI_SCHEME = "file";
    // Beans
    private final ObjectMapper mapper;

    // Internal values
    private final FileCacheLockProvider<FileCacheIndexRepositoryImpl<?>> repositoryLock;

    @Autowired
    public FileCacheIndexRepositoryImpl(final CacheConfiguration cacheConfiguration,
                                        final ObjectMapper mapper) {
        super(cacheConfiguration);
        this.mapper = mapper;
        this.ensureCacheDirectoryResourcesExists();
        repositoryLock = FileCacheLockProvider.of(indexFilePathUsingCacheDirectoryRoot());
    }

    @Override
    @SneakyThrows
    public List<CachedInputStreamIdentifier<T>> readIndex() {

        log.debug("Reading index file");
        final var indexFile = indexFilePathUsingCacheDirectoryRoot().toFile();

        try (final var lock = repositoryLock.acquireLock()) {
            if (indexFile.exists() && indexFile.length() > 0) {
                try {
                    return mapper.readValue(
                        indexFile,
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

    @Override
    @SneakyThrows
    public void persistIndex(List<CachedInputStreamIdentifier<T>> indexContent) {

        log.debug("Writing index file");
        try (final var lock = repositoryLock.acquireLock()) {
            mapper.writeValue(indexFilePathUsingCacheDirectoryRoot().toFile(), indexContent);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Path indexFilePathUsingCacheDirectoryRoot() {
        return Path.of(URI.create(String.format(
            "%s/%s",
            getCacheConfiguration().getDirectoryRootUri(DIRECTORY_ROOT_URI_SCHEME),
            LOCK_FILE_NAME
        )));
    }


    private CollectionType collectionTypeDefinitionOfCachedInputStreamIdentifierDef() {

        return mapper.getTypeFactory()
            .constructCollectionType(List.class, CachedInputStreamIdentifier.class);
    }
}
