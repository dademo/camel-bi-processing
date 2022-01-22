/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services.mappers.database;

import fr.dademo.supervision.backends.model.database.resources.DatabaseView;
import fr.dademo.supervision.entities.database.DataBackendDatabaseViewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseViewEntityMapper {

    DataBackendDatabaseViewEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseViewEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schema", ignore = true)
    DataBackendDatabaseViewEntity toDataBackendDatabaseViewEntity(DatabaseView source);
}
