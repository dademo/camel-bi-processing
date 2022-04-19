/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers;

import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendClusterNode;
import fr.dademo.supervision.dependencies.entities.DataBackendClusterNodeEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendDescriptionEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendClusterNodeEntityMapper {

    DataBackendClusterNodeEntityMapper INSTANCE = Mappers.getMapper(DataBackendClusterNodeEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "backendDescription", target = "backendDescription")
    @Mapping(source = "backendStateExecutions", target = "backendStateExecutions")
    DataBackendClusterNodeEntity toDataBackendClusterNodeEntity(
        DataBackendClusterNode source,
        DataBackendDescriptionEntity backendDescription,
        List<DataBackendStateExecutionEntity> backendStateExecutions
    );
}
