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

public class MissingMigrationFolder extends RuntimeException {

    private static final long serialVersionUID = -6694725778419195945L;

    @SuppressWarnings("rawtypes")
    public MissingMigrationFolder(Class<? extends BaseChunkJob> clazz) {
        super(String.format("Missing migration folder definition in class %s", clazz.getName()));
    }
}
