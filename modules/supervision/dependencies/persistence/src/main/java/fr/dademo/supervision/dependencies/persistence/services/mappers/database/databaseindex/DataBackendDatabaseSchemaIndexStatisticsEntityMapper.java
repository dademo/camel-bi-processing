/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers.database.databaseindex;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseIndex;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseindex.DataBackendDatabaseSchemaIndexEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseindex.DataBackendDatabaseSchemaIndexStatisticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaIndexStatisticsEntityMapper {

    DataBackendDatabaseSchemaIndexStatisticsEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaIndexStatisticsEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "index", target = "index")
    @Mapping(source = "backendStateExecution", target = "backendStateExecution")
    DataBackendDatabaseSchemaIndexStatisticsEntity toDataBackendDatabaseIndexStatisticsEntity(
        DatabaseIndex source,
        DataBackendDatabaseSchemaIndexEntity index,
        DataBackendStateExecutionEntity backendStateExecution
    );
}
