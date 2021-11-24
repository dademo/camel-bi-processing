/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository.exception;

import okhttp3.Response;

/**
 * @author dademo
 */
public class MissingResultBodyException extends BaseHttpQueryException {

    private static final long serialVersionUID = -3412548216284615256L;

    public MissingResultBodyException(Response queryResponse) {
        super("Endpoint did provided any answer", queryResponse);
    }
}
