/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers;

import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescription;
import fr.dademo.supervision.dependencies.entities.DataBackendClusterNodeEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendDescriptionEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.dependencies.entities.database.connection.DataBackendDatabaseConnectionEntity;
import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseEntity;
import fr.dademo.supervision.dependencies.entities.database.databasereplicationpeer.DataBackendDatabaseReplicationPeerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDescriptionEntityMapper {

    DataBackendDescriptionEntityMapper INSTANCE = Mappers.getMapper(DataBackendDescriptionEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "backendStateExecutions", target = "backendStateExecutions")
    @Mapping(source = "databases", target = "databases")
    @Mapping(source = "databaseConnections", target = "databaseConnections")
    @Mapping(source = "databaseReplicationPeers", target = "databaseReplicationPeers")
    @Mapping(source = "dataBackendClusterNodes", target = "dataBackendClusterNodes")
    DataBackendDescriptionEntity toDataBackendDescriptionEntity(
        DataBackendDescription source,
        List<DataBackendStateExecutionEntity> backendStateExecutions,
        List<DataBackendDatabaseEntity> databases,
        List<DataBackendDatabaseConnectionEntity> databaseConnections,
        List<DataBackendDatabaseReplicationPeerEntity> databaseReplicationPeers,
        List<DataBackendClusterNodeEntity> dataBackendClusterNodes
    );
}
