/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.mappers;

import fr.dademo.supervision.dependencies.entities.DataBackendClusterNodeEntity;
import fr.dademo.supervision.service.services.dto.DataBackendClusterNodeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendClusterNodeEntityToDtoMapper {

    DataBackendClusterNodeEntityToDtoMapper INSTANCE = Mappers.getMapper(DataBackendClusterNodeEntityToDtoMapper.class);

    @Mapping(source = "backendStateExecutionsTimestamps", target = "timestamps")
    DataBackendClusterNodeDto viewToDto(DataBackendClusterNodeEntity source, List<Date> backendStateExecutionsTimestamps);
}
