/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http_cache.repository.exception;

import java.io.Serial;

/**
 * @author dademo
 */
public class UncheckedInterruptedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3729916507679738483L;

    public UncheckedInterruptedException(InterruptedException cause) {
        super(cause);
    }
}
