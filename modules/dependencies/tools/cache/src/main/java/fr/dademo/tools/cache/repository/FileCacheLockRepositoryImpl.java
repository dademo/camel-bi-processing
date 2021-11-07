package fr.dademo.tools.cache.repository;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.stream_definitions.configuration.CacheConfiguration;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Repository
class FileCacheLockRepositoryImpl<T extends InputStreamIdentifier<?>> extends FileCacheRepositoryBase implements FileCacheLockRepository<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCacheLockRepositoryImpl.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String LOCK_FILE_NAME = "index.lock";

    @Autowired
    private CacheConfiguration cacheConfiguration;

    private static CollectionType collectionTypeDefinitionOfCachedInputStreamIdentifierDef() {

        return MAPPER.getTypeFactory()
                .constructCollectionType(List.class, CachedInputStreamIdentifier.class);
    }

    @Override
    @SneakyThrows
    public List<CachedInputStreamIdentifier<T>> readLockFile() {

        LOGGER.debug("Reading lock file");
        final var lockFile = lockFilePathUsingCacheDirectoryRoot().toFile();

        if (lockFile.exists() && lockFile.length() > 0) {
            try {
                return MAPPER.readValue(
                        lockFile,
                        collectionTypeDefinitionOfCachedInputStreamIdentifierDef()
                );
            } catch (JacksonException ex) {
                LOGGER.warn("Unable to read lock file");
                LOGGER.warn("Will clean the cache directory");
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

        LOGGER.debug("Writing lock file");
        withLockedLockFile(() -> {
            try {
                MAPPER.writeValue(
                        lockFilePathUsingCacheDirectoryRoot().toFile(),
                        lockFileContent
                );
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });

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

    private void cleanCacheResourceDirectory() throws IOException {

        for (final var file : Optional.ofNullable(cacheConfiguration.getDirectoryRootPath().toFile().listFiles()).orElse(new File[0])) {
            if (file.isDirectory()) {
                FileUtils.deleteDirectory(file);
            } else {
                FileUtils.delete(file);
            }
        }
    }

    private Path lockFilePathUsingCacheDirectoryRoot() {
        return cacheConfiguration.getDirectoryRootPath().resolve(LOCK_FILE_NAME);
    }
}
