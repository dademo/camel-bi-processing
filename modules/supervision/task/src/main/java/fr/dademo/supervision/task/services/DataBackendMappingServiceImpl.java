/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services;

import fr.dademo.supervision.backends.model.shared.DataBackendDescription;
import fr.dademo.supervision.backends.model.shared.DataBackendModuleMetaData;
import fr.dademo.supervision.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.task.services.mappers.DataBackendDescriptionMapper;
import fr.dademo.supervision.task.services.mappers.DataBackendModuleMetaDataMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@Service
public class DataBackendMappingServiceImpl implements DataBackendMappingService {

    @Nonnull
    @Override
    public DataBackendStateExecutionEntity mapModuleDataToEntity(
        @Nonnull DataBackendModuleMetaData backendModuleMetaData,
        @Nonnull DataBackendDescription dataBackendDescription) {

        return DataBackendStateExecutionEntity.builder()
            .dataBackendModuleMetaData(DataBackendModuleMetaDataMapper.INSTANCE.moduleMetaDataToEntity(backendModuleMetaData))
            .dataBackendDescription(DataBackendDescriptionMapper.INSTANCE.moduleDescriptionToEntity(dataBackendDescription))
            .globalDatabase(null)
            .build();
    }
}
