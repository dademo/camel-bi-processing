/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.configuration.CacheConfiguration;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.cache.repository.cache_index.CacheIndexRepository;
import fr.dademo.tools.cache.repository.exception.MissingCachedInputStreamException;
import fr.dademo.tools.cache.repository.exception.TempFileResolutionError;
import fr.dademo.tools.cache.repository.support.CachedInputStreamWrapper;
import fr.dademo.tools.tools.HashTools;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.vfs2.FileNotFoundException;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author dademo
 */
@Slf4j
@ConditionalOnProperty(
    value = CacheConfiguration.CONFIGURATION_PREFIX + ".enabled",
    havingValue = "true"
)
@Repository
public class VFSCacheRepositoryImpl<T extends InputStreamIdentifier<?>> extends VFSCacheRepositoryBeanLifecycle<T> implements CacheRepository<T> {

    private static final String HASH_ALGORITHM = "SHA-512";
    private static final MessageDigest HASH_COMPUTER = HashTools.getHashComputerForAlgorithm(HASH_ALGORITHM);


    @Autowired
    private CacheIndexRepository<T> cacheIndexRepository;


    @Nonnull
    @Override
    public Optional<CachedInputStreamIdentifier<T>> getCachedInputStreamIdentifierOf(InputStreamIdentifier<?> inputStreamIdentifier) {

        return cacheIndexRepository.readIndex().stream()
            .filter(cachedInputStreamIdentifier -> cachedInputStreamIdentifier.getCachedIdentifier().equals(inputStreamIdentifier))
            .findFirst();
    }

    @Nonnull
    @Override
    public InputStream readFromCachedInputStream(@Nonnull CachedInputStreamIdentifier<T> cachedInputStreamIdentifier) {

        log.debug("Will read from cache for file `{}`", cachedInputStreamIdentifier.getCachedIdentifier().getDescription());
        return Optional.ofNullable(
                getCachedFileObjectFor(cachedInputStreamIdentifier)
            )
            .map(this::openInputStream)
            .orElseThrow(() -> new MissingCachedInputStreamException(cachedInputStreamIdentifier.getCachedIdentifier()));
    }

    @Nonnull
    @Override
    @SuppressWarnings("java:S2095")
    public InputStream cacheInputStream(@Nonnull InputStream inputStream, @Nonnull T inputStreamIdentifier) throws IOException {

        log.debug("Storing input stream in cache for identifier `{}`", inputStreamIdentifier.getDescription());
        // Writing body to a temp file
        final var tempCachedFile = getTempFile();

        return CachedInputStreamWrapper.of(
            new TeeInputStream(inputStream, tempCachedFile.getContent().getOutputStream(), true),
            isValidInputStream -> {
                if (isValidInputStream) {
                    try {
                        persistCache(tempCachedFile, inputStreamIdentifier);
                    } finally {
                        try {
                            // In case of an error, we still clean the directories
                            if (tempCachedFile.exists()) {
                                log.debug("Cleaning working dir for identifier `{}`", inputStreamIdentifier.getDescription());
                                deleteFile(tempCachedFile);
                            }
                        } catch (IOException ex) {
                            log.warn("An error occurred when trying to delete the temporary file", ex);
                        }
                    }
                } else {
                    log.warn("Input stream is not valid and will not be persisted");
                }
            });
    }

    @SneakyThrows
    @Override
    public void deleteFromCache(@Nonnull T inputStreamIdentifier) {

        log.info("Removing stream {}", inputStreamIdentifier);
        try (final var lock = acquireCacheLockForIdentifier(inputStreamIdentifier)) {

            // Updating index
            final var indexFileContent = cacheIndexRepository.readIndex();
            indexFileContent.remove(buildCachedInputStreamIdentifier(inputStreamIdentifier));
            cacheIndexRepository.persistIndex(indexFileContent);

            getCachedInputStreamIdentifierOf(inputStreamIdentifier)
                .map(this::getCachedFileObjectFor)
                .ifPresent(this::deleteFile);
        }
        log.info("Removed file {}", "");
    }


    @SneakyThrows
    private synchronized void persistCache(FileObject tempCachedFile, T inputFileIdentifier) {

        log.debug("Final persisting cached identifier `{}`", inputFileIdentifier.getDescription());
        final var cachedInputStreamIdentifier = buildCachedInputStreamIdentifier(inputFileIdentifier);

        try (final var lock = acquireCacheLockForIdentifier(inputFileIdentifier)) {
            final var indexFileContent = cacheIndexRepository.readIndex();
            indexFileContent.add(cachedInputStreamIdentifier);
            final var finalCachedFileFile = Optional.ofNullable(getCachedFileObjectFor(cachedInputStreamIdentifier))
                .orElseThrow(() -> new TempFileResolutionError(getCachedFileObjectFor(cachedInputStreamIdentifier).getURI()));

            try {
                log.debug("Moving cached file");
                tempCachedFile.moveTo(finalCachedFileFile);
                log.debug("Cached file moved");
                cacheIndexRepository.persistIndex(indexFileContent);
            } catch (IOException e) {
                // Cleaning
                if (tempCachedFile.exists()) {
                    log.debug("Removing cache file `{}`", tempCachedFile.getURI());
                    deleteFile(tempCachedFile);
                }
                if (finalCachedFileFile.exists()) {
                    log.debug("Removing cache file `{}`", finalCachedFileFile.getURI());
                    deleteFile(finalCachedFileFile);
                }
                throw new UncheckedIOException(e);
            }
        }
    }

    @SneakyThrows
    private void deleteFile(FileObject file) {

        log.debug("Will delete file `{}`", file.getURI());
        final var deleted = file.deleteAll();
        log.debug("File `{}` {}deleted", file.getURI(), (deleted == 0) ? "not " : "");
    }

    @Nullable
    private InputStream openInputStream(FileObject file) {

        try {
            return file.getContent().getInputStream();
        } catch (FileSystemException ex) {

            if (ex instanceof FileNotFoundException) {
                log.error("Cached file {} not found", file.getURI());
            } else {
                log.error("Unexpected error occurred", ex);
            }
            return null;
        }
    }


    private CachedInputStreamIdentifier<T> buildCachedInputStreamIdentifier(T inputStreamIdentifier) {

        return CachedInputStreamIdentifier.<T>builder()
            .timestamp(LocalDateTime.now())
            .cachedIdentifier(inputStreamIdentifier)
            .fileName(getCachedFinalFileName(inputStreamIdentifier))
            .build();
    }

    private String getCachedFinalFileName(T inputStreamIdentifier) {
        return HashTools.computeHashString(HASH_COMPUTER, inputStreamIdentifier.getDescription().getBytes());
    }
}
