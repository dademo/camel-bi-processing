/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.exception;

import java.io.Serial;
import java.net.URI;

public class TempFileResolutionError extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1193765382704971792L;

    public TempFileResolutionError(URI fileUri) {
        super(String.format("Error in VFS management of file %s", fileUri));
    }
}
