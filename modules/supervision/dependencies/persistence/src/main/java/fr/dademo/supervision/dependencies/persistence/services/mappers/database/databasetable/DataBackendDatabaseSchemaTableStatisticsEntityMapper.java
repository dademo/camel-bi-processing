/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers.database.databasetable;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseTable;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.dependencies.entities.database.databasetable.DataBackendDatabaseSchemaTableEntity;
import fr.dademo.supervision.dependencies.entities.database.databasetable.DataBackendDatabaseSchemaTableStatisticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaTableStatisticsEntityMapper {

    DataBackendDatabaseSchemaTableStatisticsEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaTableStatisticsEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "table", target = "table")
    @Mapping(source = "backendStateExecution", target = "backendStateExecution")
    DataBackendDatabaseSchemaTableStatisticsEntity toDataBackendDatabaseTableStatisticsEntity(
        DatabaseTable source,
        DataBackendDatabaseSchemaTableEntity table,
        DataBackendStateExecutionEntity backendStateExecution
    );
}
