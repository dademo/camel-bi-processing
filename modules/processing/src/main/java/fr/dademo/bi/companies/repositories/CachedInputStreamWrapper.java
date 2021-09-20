package fr.dademo.bi.companies.repositories;

import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class CachedInputStreamWrapper extends InputStream {

    private final InputStream delegate;
    private final CloseCallback callback;

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
        delegate.close();
        this.callback.onClose();
    }

    @FunctionalInterface
    public interface CloseCallback {
        void onClose();
    }
}
