/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers.database.database;

import fr.dademo.supervision.dependencies.backends.model.database.DatabaseDescription;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseEntity;
import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseStatisticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Duration;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseStatisticsEntityMapper {

    String DURATION_TO_MILLISECONDS_MAPPER_NAME = "durationToMilliseconds";

    DataBackendDatabaseStatisticsEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseStatisticsEntityMapper.class);

    @Named(DURATION_TO_MILLISECONDS_MAPPER_NAME)
    static Long durationToMillisecondsMapper(Duration duration) {
        return duration.getSeconds() * 1000 + duration.getNano() / 1_000_000;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "database", target = "database")
    @Mapping(source = "backendStateExecution", target = "backendStateExecution")
    @Mapping(source = "source.readTime", target = "readTimeMilliseconds", qualifiedByName = DURATION_TO_MILLISECONDS_MAPPER_NAME)
    @Mapping(source = "source.writeTime", target = "writeTimeMilliseconds", qualifiedByName = DURATION_TO_MILLISECONDS_MAPPER_NAME)
    DataBackendDatabaseStatisticsEntity toDataBackendDatabaseStatisticsEntity(
        DatabaseDescription source,
        DataBackendDatabaseEntity database,
        DataBackendStateExecutionEntity backendStateExecution
    );
}
