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
import jakarta.annotation.Nonnull;

import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * @author dademo
 */
public class DefaultQueryResponseHandler implements QueryResponseHandler {

    @Override
    public InputStream handleResponse(@Nonnull HttpResponse<InputStream> response,
                                      @Nonnull HttpDataQuerierRepository httpDataQuerierRepository) {

        if (isRedirectResponse(response)) {
            throw new UnexpectedRedirectResponseException(response);
        }
        if (!isSuccessfullResponse(response)) {
            throw new FailedQueryException(response);
        }

        return Optional.ofNullable(response.body())
            .orElseThrow(() -> new MissingResultBodyException(response));
    }

    private boolean isSuccessfullResponse(HttpResponse<InputStream> response) {
        return response.statusCode() / 100 == 2;
    }

    private boolean isRedirectResponse(HttpResponse<InputStream> response) {
        return response.statusCode() / 100 == 3;
    }
}
