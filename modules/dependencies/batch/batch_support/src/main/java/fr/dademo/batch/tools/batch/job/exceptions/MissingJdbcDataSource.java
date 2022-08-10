/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job.exceptions;

import fr.dademo.batch.tools.batch.job.BaseChunkJob;

public class MissingJdbcDataSource extends RuntimeException {

    private static final long serialVersionUID = 3943516555210855477L;

    @SuppressWarnings("rawtypes")
    public MissingJdbcDataSource(Class<? extends BaseChunkJob> clazz) {
        super(String.format("Missing datasource definition in class %s", clazz.getName()));
    }
}
