/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers.database.replicationpeer;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseReplicationPeer;
import fr.dademo.supervision.dependencies.entities.DataBackendDescriptionEntity;
import fr.dademo.supervision.dependencies.entities.database.databasereplicationpeer.DataBackendDatabaseReplicationPeerEntity;
import fr.dademo.supervision.dependencies.entities.database.databasereplicationpeer.DataBackendDatabaseReplicationPeerStatisticsEntity;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.net.InetAddress;
import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseReplicationPeerEntityMapper {

    DataBackendDatabaseReplicationPeerEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseReplicationPeerEntityMapper.class);

    static String map(@Nullable InetAddress source) {

        if (source != null) {
            return source.getHostAddress();
        } else {
            return null;
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "backendDescription", target = "backendDescription")
    @Mapping(source = "replicationPeerStatistics", target = "replicationPeerStatistics")
    DataBackendDatabaseReplicationPeerEntity toDataBackendDatabaseReplicationPeerEntity(
        DatabaseReplicationPeer source,
        DataBackendDescriptionEntity backendDescription,
        List<DataBackendDatabaseReplicationPeerStatisticsEntity> replicationPeerStatistics
    );
}
