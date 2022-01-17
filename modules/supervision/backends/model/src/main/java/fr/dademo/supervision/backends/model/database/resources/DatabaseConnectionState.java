/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.model.database.resources;

import lombok.Getter;

/**
 * @author dademo
 */
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public enum DatabaseConnectionState {

    /**
     * The backend is executing a query
     */
    ACTIVE("active"),

    /**
     * The backend is waiting for a new client command
     */
    IDLE("idle"),

    /**
     * The backend is in a transaction, but is not currently executing a query
     */
    IDLE_IN_TRANSACTION("idle in transaction"),

    /**
     * This state is similar to idle in transaction, except one of the statements in the transaction caused an error
     */
    IDLE_IN_TRANSACTION_ABORTED("idle in transaction (aborted)"),

    /**
     * The backend is executing a fast-path function
     */
    FASTPATH_FUNCTION_CALL("fastpath function call"),

    /**
     * This state is reported if track_activities is disabled in this backend
     */
    DISABLED("disabled");

    @Getter
    private final String value;

    DatabaseConnectionState(String value) {
        this.value = value;
    }
}
