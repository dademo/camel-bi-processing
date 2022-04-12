/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.mappers;

import fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaTableView;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaTableDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaTableEntityToDtoMapper {

    DataBackendDatabaseSchemaTableEntityToDtoMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaTableEntityToDtoMapper.class);

    @Mapping(source = "source.dataBackendDatabaseSchemaTableEntity", target = ".")
    @Mapping(source = "source.dataBackendDatabaseSchemaId", target = "dataBackendDatabaseSchemaId")
    @Mapping(source = "source.statisticsCount", target = "statisticsCount")
    DataBackendDatabaseSchemaTableDto viewToDto(DataBackendDatabaseSchemaTableView source);
}
