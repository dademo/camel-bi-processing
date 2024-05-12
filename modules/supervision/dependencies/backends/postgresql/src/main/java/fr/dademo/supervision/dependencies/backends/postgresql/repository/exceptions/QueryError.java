/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository.exceptions;

import java.io.Serial;

/**
 * @author dademo
 */
public class QueryError extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2441792896539953973L;

    public QueryError() {
        super("Unable to perform query");
    }

    public QueryError(String message) {
        super(String.format("Unable to perform query :%n%s", message));
    }

    public QueryError(String message, Throwable cause) {
        super(String.format("Unable to perform query :%n%s", message), cause);
    }

    public QueryError(Throwable cause) {
        super("Unable to perform query", cause);
    }
}
