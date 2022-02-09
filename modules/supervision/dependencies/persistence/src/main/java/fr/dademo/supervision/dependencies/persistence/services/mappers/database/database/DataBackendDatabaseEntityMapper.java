/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers.database.database;

import fr.dademo.supervision.dependencies.backends.model.database.DatabaseDescription;
import fr.dademo.supervision.dependencies.entities.DataBackendDescriptionEntity;
import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseEntity;
import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseStatisticsEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
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
    @Mapping(source = "backendDescription", target = "backendDescription")
    @Mapping(source = "schemas", target = "schemas")
    @Mapping(source = "databaseStatistics", target = "databaseStatistics")
    DataBackendDatabaseEntity toDataBackendDatabaseEntity(
        DatabaseDescription source,
        DataBackendDescriptionEntity backendDescription,
        List<DataBackendDatabaseSchemaEntity> schemas,
        List<DataBackendDatabaseStatisticsEntity> databaseStatistics
    );
}
