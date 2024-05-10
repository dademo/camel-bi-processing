/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository.exception;

import jakarta.annotation.Nonnull;

import java.io.InputStream;
import java.io.Serial;
import java.net.http.HttpResponse;

/**
 * @author dademo
 */
public class FailedQueryException extends BaseHttpQueryException {

    @Serial
    private static final long serialVersionUID = 7406651192145018752L;

    public FailedQueryException(@Nonnull HttpResponse<InputStream> queryResponse) {
        super(String.format("Query on url [%s] failed (%s)",
            queryResponse.request().uri(),
            queryResponse
        ), queryResponse);
    }
}
