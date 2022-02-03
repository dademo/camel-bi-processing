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

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseIndexDefaultImpl;
import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseIndexEntity;

import java.util.function.Function;

/**
 * @author dademo
 */
public class DatabaseIndexValueMapper implements Function<DatabaseIndexEntity, DatabaseIndexDefaultImpl> {

    @Override
    public DatabaseIndexDefaultImpl apply(DatabaseIndexEntity databaseIndexEntity) {

        return DatabaseIndexDefaultImpl.builder()
            .name(databaseIndexEntity.getName())
            .tableName(databaseIndexEntity.getTableName())
            .indexScansCount(databaseIndexEntity.getIndexScansCount())
            .returnedRowsCount(databaseIndexEntity.getReturnedRowsCount())
            .fetchedRowsCount(databaseIndexEntity.getFetchedRowsCount())
            .build();
    }
}
