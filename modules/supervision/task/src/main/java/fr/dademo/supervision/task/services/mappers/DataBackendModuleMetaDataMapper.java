/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services.mappers;

import fr.dademo.supervision.backends.model.shared.DataBackendModuleMetaData;
import fr.dademo.supervision.entities.DataBackendModuleMetaDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendModuleMetaDataMapper {

    DataBackendModuleMetaDataMapper INSTANCE = Mappers.getMapper(DataBackendModuleMetaDataMapper.class);

    DataBackendModuleMetaDataEntity moduleMetaDataToEntity(DataBackendModuleMetaData source);
}
