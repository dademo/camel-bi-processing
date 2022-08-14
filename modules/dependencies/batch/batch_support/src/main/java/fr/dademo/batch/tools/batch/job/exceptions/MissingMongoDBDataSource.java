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

public class MissingMongoDBDataSource extends RuntimeException {

    private static final long serialVersionUID = 877723797788251132L;

    @SuppressWarnings("rawtypes")
    private MissingMongoDBDataSource(String refName, Class<? extends BaseChunkJob> clazz) {
        super(String.format("Missing %s Mongodb definition in class %s", refName, clazz.getName()));
    }

    @SuppressWarnings("rawtypes")
    public static MissingMongoDBDataSource missingInputJdbcDataSource(Class<? extends BaseChunkJob> clazz) {
        return new MissingMongoDBDataSource("input", clazz);
    }

    @SuppressWarnings("rawtypes")
    public static MissingMongoDBDataSource missingOutputJdbcDataSource(Class<? extends BaseChunkJob> clazz) {
        return new MissingMongoDBDataSource("output", clazz);
    }
}
