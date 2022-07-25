/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.lock;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.concurrent.locks.ReentrantLock;

public class MemoryCacheLockProvider implements CacheLockProvider {

    private final ReentrantLock threadLock;

    public MemoryCacheLockProvider() {
        threadLock = new ReentrantLock();
    }

    @Override
    public CacheLock acquireLock() {

        threadLock.lock();
        return new MemoryCacheLockImpl(this);
    }

    @Override
    public synchronized void releaseLock() {

        // Release the thread lock in every case
        threadLock.unlock();
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class MemoryCacheLockImpl implements CacheLock {

        private final MemoryCacheLockProvider memoryCacheLockProvider;

        @Override
        public void close() {
            memoryCacheLockProvider.releaseLock();
        }

    }
}
