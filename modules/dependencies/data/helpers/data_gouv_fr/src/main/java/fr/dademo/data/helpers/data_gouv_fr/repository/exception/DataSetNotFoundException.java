/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.helpers.data_gouv_fr.repository.exception;

import fr.dademo.reader.http.repository.exception.BaseHttpQueryException;
import jakarta.annotation.Nonnull;

/**
 * @author dademo
 */
public class DataSetNotFoundException extends BaseHttpQueryException {

    private static final long serialVersionUID = -2396308059854053152L;

    public DataSetNotFoundException(@Nonnull String dataSetTitle, @Nonnull BaseHttpQueryException cause) {
        super(
            String.format("DataSet `%s` not found using query `%s`",
                dataSetTitle, cause.getQueryResponse().request().uri()),
            cause
        );
    }
}
