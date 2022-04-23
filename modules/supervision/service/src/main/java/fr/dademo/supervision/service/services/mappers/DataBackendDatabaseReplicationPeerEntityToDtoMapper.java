/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.mappers;

import fr.dademo.supervision.service.repository.views.DataBackendDatabaseReplicationPeerView;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseReplicationPeerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseReplicationPeerEntityToDtoMapper {

    DataBackendDatabaseReplicationPeerEntityToDtoMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseReplicationPeerEntityToDtoMapper.class);

    @Mapping(source = "source.databaseReplicationPeerEntity", target = ".")
    @Mapping(source = "source.dataBackendDatabaseId", target = "dataBackendDatabaseId")
    @Mapping(source = "source.statisticsCount", target = "statisticsCount")
    DataBackendDatabaseReplicationPeerDto viewToDto(DataBackendDatabaseReplicationPeerView source);
}
