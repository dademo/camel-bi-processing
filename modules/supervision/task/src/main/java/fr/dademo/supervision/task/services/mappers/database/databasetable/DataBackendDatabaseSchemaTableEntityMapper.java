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
import fr.dademo.supervision.entities.database.databasetable.DataBackendDatabaseSchemaTableEntity;
import fr.dademo.supervision.entities.database.databasetable.DataBackendDatabaseSchemaTableStatisticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaTableEntityMapper {

    DataBackendDatabaseSchemaTableEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaTableEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schema", ignore = true)
    @Mapping(source = "statistics", target = "statistics")
    DataBackendDatabaseSchemaTableEntity toDataBackendDatabaseTableEntity(
        DatabaseTable source,
        List<DataBackendDatabaseSchemaTableStatisticsEntity> statistics
    );
}
