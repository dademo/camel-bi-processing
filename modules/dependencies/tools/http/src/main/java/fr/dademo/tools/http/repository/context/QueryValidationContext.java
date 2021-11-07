package fr.dademo.tools.http.repository.context;

import java.io.IOException;
import java.io.InputStream;

public abstract class QueryValidationContext extends InputStream {

    /**
     * {@inheritDoc}
     * <p>
     * This function also waits for all validators to end.
     *
     * @throws IOException the underlying {@link InputStream} have thrown an exception.
     */
    @Override
    public abstract void close() throws IOException;
}
