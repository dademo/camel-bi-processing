/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository.handlers;

import fr.dademo.reader.http.repository.HttpDataQuerierRepository;
import fr.dademo.reader.http.repository.exception.FailedQueryException;
import fr.dademo.reader.http.repository.exception.MissingResultBodyException;
import fr.dademo.reader.http.repository.exception.UnexpectedRedirectResponseException;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.InputStream;
import java.util.Optional;

/**
 * @author dademo
 */
public class DefaultQueryResponseHandler implements QueryResponseHandler {

    @Override
    public InputStream handleResponse(Response response, HttpDataQuerierRepository httpDataQuerierRepository) {

        if (!response.isSuccessful()) {
            throw new FailedQueryException(response);
        }
        if (response.isRedirect()) {
            throw new UnexpectedRedirectResponseException(response);
        }

        return Optional.ofNullable(response.body())
            .map(ResponseBody::byteStream)
            .orElseThrow(() -> new MissingResultBodyException(response));
    }
}
