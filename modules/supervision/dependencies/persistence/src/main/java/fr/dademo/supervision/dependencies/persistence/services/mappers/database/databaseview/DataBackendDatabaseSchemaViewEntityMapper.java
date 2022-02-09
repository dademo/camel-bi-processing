/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers.database.databaseview;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseView;
import fr.dademo.supervision.dependencies.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseview.DataBackendDatabaseSchemaViewEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseview.DataBackendDatabaseSchemaViewStatisticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaViewEntityMapper {

    DataBackendDatabaseSchemaViewEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaViewEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "source.name", target = "name")   // Resolving a conflict
    @Mapping(source = "schema", target = "schema")
    @Mapping(source = "statistics", target = "statistics")
    DataBackendDatabaseSchemaViewEntity toDataBackendDatabaseViewEntity(
        DatabaseView source,
        DataBackendDatabaseSchemaEntity schema,
        List<DataBackendDatabaseSchemaViewStatisticsEntity> statistics
    );
}
