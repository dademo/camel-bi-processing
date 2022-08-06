/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.exceptions;

public abstract class ResourceWrapperException extends RuntimeException {

    private static final long serialVersionUID = -7218270755909144932L;


    public ResourceWrapperException(String message) {
        super(message);
    }

    public ResourceWrapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
