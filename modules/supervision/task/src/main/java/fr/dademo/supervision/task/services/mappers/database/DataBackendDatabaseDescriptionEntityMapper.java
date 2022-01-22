/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services.mappers.database;

import fr.dademo.supervision.backends.model.database.DatabaseDescription;
import fr.dademo.supervision.entities.database.DataBackendDatabaseDescriptionEntity;
import fr.dademo.supervision.entities.database.DataBackendDatabaseSchemaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Duration;
import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseDescriptionEntityMapper {

    String DURATION_TO_MILLISECONDS_MAPPER_NAME = "durationToMilliseconds";

    DataBackendDatabaseDescriptionEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseDescriptionEntityMapper.class);

    @Named(DURATION_TO_MILLISECONDS_MAPPER_NAME)
    static Long durationToMillisecondsMapper(Duration duration) {
        return duration.getSeconds() * 1000 + duration.getNano() / 1_000_000;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "globalDatabase", ignore = true)
    @Mapping(source = "schemas", target = "schemas")
    @Mapping(source = "source.readTime", target = "readTimeMilliseconds", qualifiedByName = DURATION_TO_MILLISECONDS_MAPPER_NAME)
    @Mapping(source = "source.writeTime", target = "writeTimeMilliseconds", qualifiedByName = DURATION_TO_MILLISECONDS_MAPPER_NAME)
    DataBackendDatabaseDescriptionEntity toDataBackendDatabaseDescriptionEntity(
        DatabaseDescription source,
        List<DataBackendDatabaseSchemaEntity> schemas
    );
}
