/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services.mappers;

import fr.dademo.supervision.backends.model.shared.DataBackendDescription;
import fr.dademo.supervision.entities.DataBackendDescriptionEntity;
import fr.dademo.supervision.entities.DataBackendStateExecutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDescriptionMapper {

    DataBackendDescriptionMapper INSTANCE = Mappers.getMapper(DataBackendDescriptionMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "backendStateExecutions", target = "backendStateExecutions")
    DataBackendDescriptionEntity moduleDescriptionToEntity(
        DataBackendDescription source,
        List<DataBackendStateExecutionEntity> backendStateExecutions
    );
}
