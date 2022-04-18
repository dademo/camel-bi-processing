/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.service.mappers;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseReplicationPeer;
import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseReplicationPeerDefaultImpl;
import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseReplicationPeerEntity;

import java.util.function.Function;

/**
 * @author dademo
 */
public class DatabaseReplicationPeerValueMapper implements Function<DatabaseReplicationPeerEntity, DatabaseReplicationPeer> {

    @Override
    public DatabaseReplicationPeer apply(DatabaseReplicationPeerEntity sourceObject) {

        return DatabaseReplicationPeerDefaultImpl.builder()
            .isActive(true)
            .targetDatabase(null)
            .isPrimary(sourceObject.getIsPrimary())
            .status(sourceObject.getStatus())
            .replicationPID(sourceObject.getReplicationPID())
            .useName(sourceObject.getUseName())
            .applicationName(sourceObject.getApplicationName())
            .slotName(sourceObject.getSlotName())
            .peerAddress(sourceObject.getPeerAddress())
            .peerHostName(sourceObject.getPeerHostName())
            .peerPort(sourceObject.getPeerPort())
            .state(sourceObject.getState())
            .syncState(sourceObject.getSyncState())
            .replicationDelay(sourceObject.getReplicationDelay())
            .sendingDelaySize(sourceObject.getSendingDelaySize())
            .receivingDelaySize(sourceObject.getReceivingDelaySize())
            .replayingDelaySize(sourceObject.getReplayingDelaySize())
            .totalDelaySize(sourceObject.getTotalDelaySize())
            .build();
    }
}
