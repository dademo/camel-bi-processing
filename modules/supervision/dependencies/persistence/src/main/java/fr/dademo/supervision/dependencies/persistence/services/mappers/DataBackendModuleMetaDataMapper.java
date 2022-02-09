/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers;

import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendModuleMetaData;
import fr.dademo.supervision.dependencies.entities.DataBackendModuleMetaDataEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendModuleMetaDataMapper {

    DataBackendModuleMetaDataMapper INSTANCE = Mappers.getMapper(DataBackendModuleMetaDataMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "backendStateExecutions", target = "backendStateExecutions")
    DataBackendModuleMetaDataEntity moduleMetaDataToEntity(
        DataBackendModuleMetaData source,
        List<DataBackendStateExecutionEntity> backendStateExecutions
    );
}
