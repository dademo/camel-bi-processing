/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.helpers.exception;

/**
 * @author dademo
 */
public class UnsupportedExchangeTypeException extends RuntimeException {

    private static final long serialVersionUID = 6931826922384183235L;

    public UnsupportedExchangeTypeException(String exchangeType) {
        super(String.format("Unsupported exchange type `%s`", exchangeType));
    }
}
