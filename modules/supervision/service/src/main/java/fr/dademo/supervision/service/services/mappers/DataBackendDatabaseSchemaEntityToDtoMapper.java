/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.mappers;

import fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaView;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaEntityToDtoMapper {

    DataBackendDatabaseSchemaEntityToDtoMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaEntityToDtoMapper.class);

    @Mapping(source = "source.dataBackendDatabaseSchemaEntity", target = ".")
    @Mapping(source = "source.dataBackendDatabaseId", target = "dataBackendDatabaseId")
    @Mapping(source = "source.tablesCount", target = "tablesCount")
    @Mapping(source = "source.schemasCount", target = "schemasCount")
    @Mapping(source = "source.indexesCount", target = "indexesCount")
    DataBackendDatabaseSchemaDto viewToDto(DataBackendDatabaseSchemaView source);
}
