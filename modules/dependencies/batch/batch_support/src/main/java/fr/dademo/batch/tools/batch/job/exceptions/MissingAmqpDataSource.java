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

public class MissingAmqpDataSource extends RuntimeException {

    private static final long serialVersionUID = 2362583254535190022L;

    private MissingAmqpDataSource(String refName, Class<? extends BaseChunkedJob> clazz) {
        super(String.format("Missing %s amqp definition in class %s", refName, clazz.getName()));
    }

    public static MissingAmqpDataSource missingInputJdbcDataSource(Class<? extends BaseChunkedJob> clazz) {
        return new MissingAmqpDataSource("input", clazz);
    }

    public static MissingAmqpDataSource missingOutputJdbcDataSource(Class<? extends BaseChunkedJob> clazz) {
        return new MissingAmqpDataSource("output", clazz);
    }
}
