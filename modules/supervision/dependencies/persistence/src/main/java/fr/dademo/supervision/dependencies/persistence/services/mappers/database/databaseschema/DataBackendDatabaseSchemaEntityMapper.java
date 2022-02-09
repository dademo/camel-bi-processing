/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers.database.databaseschema;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseSchema;
import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseindex.DataBackendDatabaseSchemaIndexEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
import fr.dademo.supervision.dependencies.entities.database.databasetable.DataBackendDatabaseSchemaTableEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseview.DataBackendDatabaseSchemaViewEntity;
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
    @Mapping(source = "source.name", target = "name")   // Resolving a conflict
    @Mapping(source = "database", target = "database")
    @Mapping(source = "tables", target = "tables")
    @Mapping(source = "views", target = "views")
    @Mapping(source = "indexes", target = "indexes")
    DataBackendDatabaseSchemaEntity toDataBackendDatabaseSchemaEntity(
        DatabaseSchema source,
        DataBackendDatabaseEntity database,
        List<DataBackendDatabaseSchemaTableEntity> tables,
        List<DataBackendDatabaseSchemaViewEntity> views,
        List<DataBackendDatabaseSchemaIndexEntity> indexes
    );

}
