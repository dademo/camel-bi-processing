/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.mappers;

import fr.dademo.supervision.dependencies.entities.database.databasetable.DataBackendDatabaseSchemaTableStatisticsEntity;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaTableStatisticsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaTableStatisticsEntityToDtoMapper {

    DataBackendDatabaseSchemaTableStatisticsEntityToDtoMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaTableStatisticsEntityToDtoMapper.class);

    @Mapping(source = "source.backendStateExecution.timestamp", target = "timestamp")
    DataBackendDatabaseSchemaTableStatisticsDto viewToDto(DataBackendDatabaseSchemaTableStatisticsEntity source);
}
