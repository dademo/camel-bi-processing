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
public class UnexpectedRedirectResponseException extends BaseHttpQueryException {

    private static final long serialVersionUID = 4537658660276565516L;

    public UnexpectedRedirectResponseException(Response queryResponse) {
        super("Unexpected redirection answer", queryResponse);
    }
}
