/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository.exception;

import java.io.InputStream;
import java.io.Serial;
import java.net.http.HttpResponse;

/**
 * @author dademo
 */
public class MissingResultBodyException extends BaseHttpQueryException {

    @Serial
    private static final long serialVersionUID = -3412548216284615256L;

    public MissingResultBodyException(HttpResponse<InputStream> queryResponse) {
        super("Endpoint did provided any answer", queryResponse);
    }
}
