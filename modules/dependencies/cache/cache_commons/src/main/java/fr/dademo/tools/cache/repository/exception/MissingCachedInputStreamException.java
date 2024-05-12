/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.exception;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;

import java.io.Serial;

/**
 * @author dademo
 */
public class MissingCachedInputStreamException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1347099597070844462L;

    public MissingCachedInputStreamException(InputStreamIdentifier<?> inputStreamIdentifier) {
        super(String.format("Missing cache for input stream `%s`", inputStreamIdentifier.getDescription()));
    }
}
