/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.services.exceptions;

import java.io.Serial;

public class MissingDataSetException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 9070873284360395744L;

    private MissingDataSetException(String message) {
        super(message);
    }

    public static MissingDataSetException byName(String name) {
        return new MissingDataSetException(String.format("Unable to get a dataset named [%s]", name));
    }

    public static MissingDataSetException byNameWithParentId(String name, String parentId) {
        return new MissingDataSetException(String.format("Unable to get a dataset named [%s] with parent [%s]", name, parentId));
    }

    public static MissingDataSetException byNameWithSourceAndSourceSub(String name, String source, String sourceSub) {
        return new MissingDataSetException(String.format("Unable to get a dataset named %s with source [%s] and source sub [%s]", name, source, sourceSub));
    }
}
