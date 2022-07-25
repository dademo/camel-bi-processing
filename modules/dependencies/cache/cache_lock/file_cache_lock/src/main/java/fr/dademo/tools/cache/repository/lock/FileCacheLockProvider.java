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

package fr.dademo.tools.cache.repository.lock;

import fr.dademo.tools.cache.repository.cache_index.CacheIndexRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileCacheLockProvider<T extends CacheIndexRepository<?>> extends MemoryCacheLockProvider implements CacheLockProvider {

    private final FileChannel fileChannel;
    private FileLock fileLock;

    protected FileCacheLockProvider(@Nonnull FileChannel fileChannel) {

        this.fileChannel = fileChannel;
        fileLock = null;
    }

    public static <T extends CacheIndexRepository<?>> FileCacheLockProvider<T> of(@Nonnull File file) {
        return of(file.toPath());
    }

    @SneakyThrows
    public static <T extends CacheIndexRepository<?>> FileCacheLockProvider<T> of(@Nonnull Path path) {

        return new FileCacheLockProvider<>(
            FileChannel.open(
                path,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)
        );
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("resource")
    synchronized public CacheLock acquireLock() {

        // JVM memory lock
        super.acquireLock();
        // Exclusive lock on the file
        fileLock = fileChannel.lock(0L, Long.MAX_VALUE, false);

        return new FileCacheLockImpl(this);
    }

    @SneakyThrows
    @Override
    public synchronized void releaseLock() {

        // We first release the file lock
        fileLock.close();
        fileLock = null;
        // We release the JVM lock
        super.releaseLock();
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class FileCacheLockImpl implements CacheLock {

        private final FileCacheLockProvider<?> fileCacheLockProvider;

        @SneakyThrows
        @Override
        public void close() {
            fileCacheLockProvider.releaseLock();
        }
    }
}
