/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services.mappers.database;

import fr.dademo.supervision.backends.model.database.resources.DatabaseSchema;
import fr.dademo.supervision.entities.database.DataBackendDatabaseIndexEntity;
import fr.dademo.supervision.entities.database.DataBackendDatabaseSchemaEntity;
import fr.dademo.supervision.entities.database.DataBackendDatabaseTableEntity;
import fr.dademo.supervision.entities.database.DataBackendDatabaseViewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaEntityMapper {

    DataBackendDatabaseSchemaEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "database", ignore = true)
    @Mapping(source = "tables", target = "tables")
    @Mapping(source = "views", target = "views")
    @Mapping(source = "indexes", target = "indexes")
    DataBackendDatabaseSchemaEntity toDataBackendDatabaseSchemaEntity(
        DatabaseSchema source,
        List<DataBackendDatabaseTableEntity> tables,
        List<DataBackendDatabaseViewEntity> views,
        List<DataBackendDatabaseIndexEntity> indexes
    );

}
