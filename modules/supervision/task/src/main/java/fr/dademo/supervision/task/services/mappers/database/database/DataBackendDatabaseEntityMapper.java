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

package fr.dademo.supervision.task.services.mappers.database.database;

import fr.dademo.supervision.backends.model.database.DatabaseDescription;
import fr.dademo.supervision.entities.database.database.DataBackendDatabaseEntity;
import fr.dademo.supervision.entities.database.database.DataBackendDatabaseStatisticsEntity;
import fr.dademo.supervision.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
import fr.dademo.supervision.entities.database.globaldatabase.DataBackendGlobalDatabaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseEntityMapper {

    String DURATION_TO_MILLISECONDS_MAPPER_NAME = "durationToMilliseconds";

    DataBackendDatabaseEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "globalDatabase", target = "globalDatabase")
    @Mapping(source = "schemas", target = "schemas")
    @Mapping(source = "databaseStatistics", target = "databaseStatistics")
    DataBackendDatabaseEntity toDataBackendDatabaseEntity(
        DatabaseDescription source,
        DataBackendGlobalDatabaseEntity globalDatabase,
        List<DataBackendDatabaseSchemaEntity> schemas,
        List<DataBackendDatabaseStatisticsEntity> databaseStatistics
    );
}
