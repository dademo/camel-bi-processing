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

package fr.dademo.supervision.dependencies.backends.postgresql.service.mappers;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseViewDefaultImpl;
import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseTableEntity;

import java.util.function.Function;

/**
 * @author dademo
 */
public class DatabaseViewValueMapper implements Function<DatabaseTableEntity, DatabaseViewDefaultImpl> {

    @Override
    public DatabaseViewDefaultImpl apply(DatabaseTableEntity databaseTableEntity) {

        return DatabaseViewDefaultImpl.builder()
            .name(databaseTableEntity.getName())
            .expression(databaseTableEntity.getViewExpression())
            .build();
    }
}
