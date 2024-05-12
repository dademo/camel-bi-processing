/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.exception;

import java.io.Serial;
import java.net.URI;

/**
 * @author dademo
 */
public class NotADirectoryException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1347099597070844462L;

    public NotADirectoryException(URI uri) {
        super(String.format("URI %s is not a directory", uri));
    }

    public NotADirectoryException(String path) {
        super(String.format("Path %s is not a directory", path));
    }
}
