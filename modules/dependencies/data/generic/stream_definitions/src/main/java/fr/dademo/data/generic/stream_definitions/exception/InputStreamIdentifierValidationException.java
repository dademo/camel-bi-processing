/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.generic.stream_definitions.exception;

import java.io.IOException;

/**
 * @author dademo
 */
public abstract class InputStreamIdentifierValidationException extends IOException {

    private static final long serialVersionUID = -3976056791520761440L;

    protected InputStreamIdentifierValidationException() {
    }

    protected InputStreamIdentifierValidationException(String message) {
        super(message);
    }

    protected InputStreamIdentifierValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    protected InputStreamIdentifierValidationException(Throwable cause) {
        super(cause);
    }
}
