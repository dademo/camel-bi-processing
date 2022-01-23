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

package fr.dademo.supervision.task.services.mappers.database.databasetable;

import fr.dademo.supervision.backends.model.database.resources.DatabaseTable;
import fr.dademo.supervision.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.entities.database.databasetable.DataBackendDatabaseSchemaTableStatisticsEntity;
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
    @Mapping(target = "table", ignore = true)
    @Mapping(source = "backendStateExecution", target = "backendStateExecution")
    DataBackendDatabaseSchemaTableStatisticsEntity toDataBackendDatabaseTableStatisticsEntity(
        DatabaseTable source,
        DataBackendStateExecutionEntity backendStateExecution
    );
}
