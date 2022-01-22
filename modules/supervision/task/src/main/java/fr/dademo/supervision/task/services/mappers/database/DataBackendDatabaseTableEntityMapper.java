/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services.mappers.database;

import fr.dademo.supervision.backends.model.database.resources.DatabaseTable;
import fr.dademo.supervision.entities.database.DataBackendDatabaseTableEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseTableEntityMapper {

    DataBackendDatabaseTableEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseTableEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schema", ignore = true)
    DataBackendDatabaseTableEntity toDataBackendDatabaseTableEntity(DatabaseTable source);
}
