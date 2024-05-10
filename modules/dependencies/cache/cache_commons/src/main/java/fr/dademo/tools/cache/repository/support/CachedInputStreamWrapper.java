/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.support;

import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author dademo
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CachedInputStreamWrapper extends InputStream {

    @Nonnull
    private final InputStream delegate;

    @Nonnull
    private final CloseCallback callback;

    public static CachedInputStreamWrapper of(@Nonnull InputStream delegate,
                                              @Nonnull CloseCallback callback) {
        return new CachedInputStreamWrapper(delegate, callback);
    }


    @Override
    public int read() throws IOException {
        return delegate.read();
    }

    @Override
    public int read(@Nonnull byte[] b, int off, int len) throws IOException {
        return delegate.read(b, off, len);
    }

    @Override
    public void close() throws IOException {

        boolean isValidInputStream = true;
        try {
            try {
                delegate.close();
            } catch (Exception e) {
                isValidInputStream = false;
                throw e;
            }
        } finally {
            // We MUST call this callback to clean values for example
            this.callback.onClose(isValidInputStream);
        }
    }

    @FunctionalInterface
    public interface CloseCallback {
        void onClose(boolean isValidInputStream);
    }
}
