/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.configuration.CacheConfiguration;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author dademo
 */
@Slf4j
@Repository
class FileCacheLockRepositoryImpl<T extends InputStreamIdentifier<?>> extends FileCacheRepositoryBase implements CacheLockRepository<T> {

    @Autowired
    private CacheConfiguration cacheConfiguration;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @SneakyThrows
    public List<CachedInputStreamIdentifier<T>> readLockFile() {

        log.debug("Reading lock file");
        final var lockFile = lockFilePathUsingCacheDirectoryRoot().toFile();

        if (lockFile.exists() && lockFile.length() > 0) {
            try {
                return mapper.readValue(
                    lockFile,
                    collectionTypeDefinitionOfCachedInputStreamIdentifierDef()
                );
            } catch (JacksonException ex) {
                log.warn("Unable to read lock file");
                log.warn("Will clean the cache directory");
                cleanCacheResourceDirectory();
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * We assume the lock file is already locked and no other process/thread will try to read it.
     * <p>
     * TODO
     */
    @Override
    public void persistLockFile(List<CachedInputStreamIdentifier<T>> lockFileContent) {

        log.debug("Writing lock file");
        try {
            mapper.writeValue(
                lockFilePathUsingCacheDirectoryRoot().toFile(),
                lockFileContent
            );
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    @SneakyThrows
    public void withLockedLockFile(Runnable onLockAcquired) {

        withLockedLockFile(() -> {
            onLockAcquired.run();
            return null;
        });
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unused")
    public synchronized <R> R withLockedLockFile(Supplier<R> onLockAcquired) {

        // Locking index file
        try (final var fileChannel = FileChannel.open(
            lockFilePathUsingCacheDirectoryRoot(),
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND)) {

            try (final var lock = fileChannel.lock()) {
                return onLockAcquired.get();
            }
        }
    }

    private Path lockFilePathUsingCacheDirectoryRoot() {
        return cacheConfiguration.getDirectoryRootPath().resolve(LOCK_FILE_NAME);
    }

    private CollectionType collectionTypeDefinitionOfCachedInputStreamIdentifierDef() {

        return mapper.getTypeFactory()
            .constructCollectionType(List.class, CachedInputStreamIdentifier.class);
    }
}
