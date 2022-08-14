/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.amqp.exception;

public class AmqpUnknownBackendException extends RuntimeException {

    private static final long serialVersionUID = 3956233356285015351L;

    public AmqpUnknownBackendException(String backendName) {
        super(String.format("Unknown backend %s", backendName));
    }
}
