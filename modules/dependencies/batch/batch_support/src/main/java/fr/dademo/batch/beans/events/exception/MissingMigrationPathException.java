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

package fr.dademo.batch.beans.events.exception;

import java.util.function.Supplier;

/**
 * @author dademo
 */
public class MissingMigrationPathException extends RuntimeException {

    private static final long serialVersionUID = 8477372149460991444L;

    public MissingMigrationPathException(String dataSourceName) {
        super(String.format("Missing migration path configuration for data source `%s`", dataSourceName));
    }

    public static Supplier<MissingMigrationPathException> ofDataSource(String dataSourceName) {
        return () -> new MissingMigrationPathException(dataSourceName);
    }
}
