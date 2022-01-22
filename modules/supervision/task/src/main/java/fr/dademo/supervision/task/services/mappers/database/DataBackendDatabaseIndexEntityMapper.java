/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services.mappers.database;

import fr.dademo.supervision.backends.model.database.resources.DatabaseIndex;
import fr.dademo.supervision.entities.database.DataBackendDatabaseIndexEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseIndexEntityMapper {

    DataBackendDatabaseIndexEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseIndexEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schema", ignore = true)
    DataBackendDatabaseIndexEntity toDataBackendDatabaseIndexEntity(DatabaseIndex source);
}
