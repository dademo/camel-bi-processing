/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.exceptions;

import jakarta.validation.constraints.Min;

public class MissingColumnException extends ResourceWrapperException {

    private static final long serialVersionUID = -2966385346119596618L;


    public MissingColumnException(String columnName) {
        super(formatMissingColumnName(columnName));
    }

    public MissingColumnException(String columnName, Throwable cause) {
        super(formatMissingColumnName(columnName), cause);
    }

    public MissingColumnException(@Min(1) int columnIndex) {
        super(formatMissingIndex(columnIndex));
    }

    public MissingColumnException(@Min(1) int columnIndex, Throwable cause) {
        super(formatMissingIndex(columnIndex), cause);
    }

    private static String formatMissingColumnName(String columnName) {

        return String.format(
            "Column [%s] does not exists",
            columnName
        );
    }

    private static String formatMissingIndex(int columnIndex) {

        return String.format(
            "Column number %d does not exists",
            columnIndex
        );
    }
}
