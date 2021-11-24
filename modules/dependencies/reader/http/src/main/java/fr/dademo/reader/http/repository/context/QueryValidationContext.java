/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository.context;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author dademo
 */
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
