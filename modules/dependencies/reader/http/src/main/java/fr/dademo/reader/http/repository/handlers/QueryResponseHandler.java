/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository.handlers;

import fr.dademo.reader.http.repository.HttpDataQuerierRepository;
import okhttp3.Response;

import java.io.InputStream;

/**
 * @author dademo
 */
@FunctionalInterface
public interface QueryResponseHandler {

    InputStream handleResponse(Response response, HttpDataQuerierRepository httpDataQuerierRepository);
}
