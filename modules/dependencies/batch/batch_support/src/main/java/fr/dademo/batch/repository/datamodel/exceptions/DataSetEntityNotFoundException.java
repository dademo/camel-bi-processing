/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.repository.datamodel.exceptions;

import java.io.Serial;

public class DataSetEntityNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1203957383446069332L;

    public DataSetEntityNotFoundException() {
        super("DataSet entity not found");
    }
}
