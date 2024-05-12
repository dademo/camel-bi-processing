/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.data_sources.exception;

import java.io.Serial;

/**
 * @author dademo
 */
public abstract class MissingConfigurationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3189249394504823141L;

    protected MissingConfigurationException(String elementName, String missingKey) {
        super(String.format("Missing key `%s` for element `%s`", missingKey, elementName));
    }
}
