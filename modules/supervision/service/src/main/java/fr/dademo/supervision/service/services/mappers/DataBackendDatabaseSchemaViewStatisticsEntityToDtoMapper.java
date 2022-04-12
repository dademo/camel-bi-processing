/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.mappers;

import fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaViewStatisticsView;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaViewStatisticsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaViewStatisticsEntityToDtoMapper {

    DataBackendDatabaseSchemaViewStatisticsEntityToDtoMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaViewStatisticsEntityToDtoMapper.class);

    @Mapping(source = "source.dataBackendDatabaseSchemaViewStatisticsEntity", target = ".")
    @Mapping(source = "source.timestamp", target = "timestamp")
    DataBackendDatabaseSchemaViewStatisticsDto viewToDto(DataBackendDatabaseSchemaViewStatisticsView source);
}
