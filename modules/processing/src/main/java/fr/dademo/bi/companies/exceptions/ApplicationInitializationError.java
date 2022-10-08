/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.exceptions;

public class ApplicationInitializationError extends RuntimeException {

    private static final long serialVersionUID = -883358055237652570L;

    public ApplicationInitializationError(String message) {
        super(message);
    }
}
