/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository.exception;

import okhttp3.Response;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
public class FailedQueryException extends BaseHttpQueryException {

    private static final long serialVersionUID = 7406651192145018752L;

    public FailedQueryException(@Nonnull Response queryResponse) {
        super(String.format("Query on url [%s] failed (%s)",
            queryResponse.request().url(),
            queryResponse
        ), queryResponse);
    }
}
