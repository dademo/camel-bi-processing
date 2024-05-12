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

import fr.dademo.batch.tools.batch.job.BaseChunkedJob;

import java.io.Serial;

@SuppressWarnings("unused")
public class MissingMongoDBDataSource extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 877723797788251132L;

    private MissingMongoDBDataSource(String refName, Class<? extends BaseChunkedJob> clazz) {
        super(String.format("Missing %s Mongodb definition in class %s", refName, clazz.getName()));
    }

    public static MissingMongoDBDataSource missingInputJdbcDataSource(Class<? extends BaseChunkedJob> clazz) {
        return new MissingMongoDBDataSource("input", clazz);
    }

    public static MissingMongoDBDataSource missingOutputJdbcDataSource(Class<? extends BaseChunkedJob> clazz) {
        return new MissingMongoDBDataSource("output", clazz);
    }
}
