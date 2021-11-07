package fr.dademo.tools.cache.repository.support;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;

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

        try {
            delegate.close();
        } finally {
            // We MUST call this callback to clean values for example
            this.callback.onClose();
        }
    }

    @FunctionalInterface
    public interface CloseCallback {
        void onClose();
    }
}
