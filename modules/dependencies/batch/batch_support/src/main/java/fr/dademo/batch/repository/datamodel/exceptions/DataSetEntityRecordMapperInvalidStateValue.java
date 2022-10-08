/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.repository.datamodel.exceptions;

public class DataSetEntityRecordMapperInvalidStateValue extends RuntimeException {

    private static final long serialVersionUID = 7580056720820655108L;

    public DataSetEntityRecordMapperInvalidStateValue(String value) {
        super(String.format("Invalid value for [state] field when reading mapping, got %s", value));
    }
}
