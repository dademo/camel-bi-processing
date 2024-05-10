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
public class UnexpectedRedirectResponseException extends BaseHttpQueryException {

    @Serial
    private static final long serialVersionUID = 4537658660276565516L;

    public UnexpectedRedirectResponseException(HttpResponse<InputStream> queryResponse) {
        super("Unexpected redirection answer", queryResponse);
    }
}
