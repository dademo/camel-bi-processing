/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.mappers;

import fr.dademo.supervision.service.repository.views.DataBackendDatabaseView;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseEntityToDtoMapper {

    DataBackendDatabaseEntityToDtoMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseEntityToDtoMapper.class);

    @Mapping(source = "source.dataBackendDatabaseEntity", target = ".")
    @Mapping(source = "source.dataBackendDescriptionId", target = "dataBackendDescriptionId")
    @Mapping(source = "source.databaseStatisticsCount", target = "databaseStatisticsCount")
    @Mapping(source = "source.schemasCount", target = "schemasCount")
    DataBackendDatabaseDto viewToDto(DataBackendDatabaseView source);
}
