/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.mappers;

import fr.dademo.supervision.service.repository.views.DataBackendDescriptionView;
import fr.dademo.supervision.service.services.dto.DataBackendDescriptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDescriptionEntityToDtoMapper {

    DataBackendDescriptionEntityToDtoMapper INSTANCE = Mappers.getMapper(DataBackendDescriptionEntityToDtoMapper.class);

    @Mapping(source = "source.dataBackendDescriptionEntity", target = ".")
    @Mapping(source = "source.backendStateExecutionsCount", target = "backendStateExecutionsCount")
    @Mapping(source = "source.databasesCount", target = "databasesCount")
    DataBackendDescriptionDto viewToDto(DataBackendDescriptionView source);
}
