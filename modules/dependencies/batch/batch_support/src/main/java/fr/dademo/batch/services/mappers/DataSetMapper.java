/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.services.mappers;

import fr.dademo.batch.repository.datamodel.DataSetEntity;
import fr.dademo.batch.services.dto.DataSetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Mapper
public interface DataSetMapper {

    DataSetMapper INSTANCE = Mappers.getMapper(DataSetMapper.class);

    DataSetDto dataSetEntityToDataSetDto(DataSetEntity dataSetEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "dataSetEntity", target = ".")
    @Mapping(source = "timestamp", target = "timestamp")
    DataSetEntity dataSetDtoToDataSetEntity(DataSetDto dataSetEntity, LocalDateTime timestamp);
}
