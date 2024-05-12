/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.job.persistence_service.consumer.exception;

import java.io.Serial;

/**
 * @author dademo
 */
public class InconsistentMessageException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2998580592176883783L;

    private InconsistentMessageException(String message) {
        super(message);
    }

    public InconsistentMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public static InconsistentMessageException forInconsistentHeaderField(String inconsistentHeaderField) {
        return withMessage(String.format("inconsistent field [%s] of message", inconsistentHeaderField));
    }

    public static InconsistentMessageException withMessage(String message) {
        return new InconsistentMessageException(String.format("Inconsistent message : %s", message));
    }

    public static InconsistentMessageException withMessage(String message, Throwable t) {
        return new InconsistentMessageException(String.format("Inconsistent message : %s", message), t);
    }
}
