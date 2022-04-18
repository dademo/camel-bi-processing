/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers.database.replicationpeer;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseReplicationPeer;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.dependencies.entities.database.databasereplicationpeer.DataBackendDatabaseReplicationPeerEntity;
import fr.dademo.supervision.dependencies.entities.database.databasereplicationpeer.DataBackendDatabaseReplicationPeerStatisticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Duration;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseReplicationPeerStatisticsEntityMapper {

    String DURATION_TO_MILLISECONDS_MAPPER_NAME = "durationToMilliseconds";

    DataBackendDatabaseReplicationPeerStatisticsEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseReplicationPeerStatisticsEntityMapper.class);

    @Named(DURATION_TO_MILLISECONDS_MAPPER_NAME)
    static Long durationToMillisecondsMapper(Duration duration) {
        return duration.getSeconds() * 1000 + duration.getNano() / 1_000_000;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "replicationPeer", target = "replicationPeer")
    @Mapping(source = "backendStateExecution", target = "backendStateExecution")
    @Mapping(source = "source.replicationDelay", target = "replicationDelayMilliseconds", qualifiedByName = DURATION_TO_MILLISECONDS_MAPPER_NAME)
    DataBackendDatabaseReplicationPeerStatisticsEntity toDataBackendDatabaseReplicationPeerStatisticsEntity(
        DatabaseReplicationPeer source,
        DataBackendDatabaseReplicationPeerEntity replicationPeer,
        DataBackendStateExecutionEntity backendStateExecution
    );
}
