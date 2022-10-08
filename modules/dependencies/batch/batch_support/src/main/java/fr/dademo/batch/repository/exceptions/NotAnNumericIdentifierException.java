/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.repository.exceptions;

import fr.dademo.batch.repository.DataSetRepository;

/**
 * @author dademo
 */
public class NotAnNumericIdentifierException extends RuntimeException {

    private static final long serialVersionUID = -7618898969980265203L;

    private static final String MESSAGE_TEMPLATE = "This repository can only work with numeric identifiers and you provided the value [%s]. Please fix it or use another implementation of the [%s] interface.";

    public NotAnNumericIdentifierException(String providedValue) {
        super(String.format(MESSAGE_TEMPLATE, providedValue, DataSetRepository.class.getName()));
    }

    public NotAnNumericIdentifierException(Throwable cause, String providedValue) {
        super(
            String.format(MESSAGE_TEMPLATE, providedValue, DataSetRepository.class.getName()),
            cause
        );
    }
}
