/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.service.mappers;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseConnection;
import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseConnectionDefaultImpl;
import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseConnectionEntity;

import java.util.function.Function;

/**
 * @author dademo
 */
public class DatabaseConnectionValueMapper implements Function<DatabaseConnectionEntity, DatabaseConnection> {

    @Override
    public DatabaseConnection apply(DatabaseConnectionEntity sourceObject) {

        return DatabaseConnectionDefaultImpl.builder()
            .connectionState(sourceObject.getConnectionState())
            .connectionPID(sourceObject.getConnectionPID())
            .connectedDatabaseName(sourceObject.getConnectedDatabaseName())
            .userName(sourceObject.getUserName())
            .applicationName(sourceObject.getApplicationName())
            .clientAddress(sourceObject.getClientAddress())
            .clientHostname(sourceObject.getClientHostname())
            .clientPort(sourceObject.getClientPort())
            .connectionStart(sourceObject.getConnectionStart())
            .transactionStart(sourceObject.getTransactionStart())
            .lastQueryStart(sourceObject.getLastQueryStart())
            .lastStateChange(sourceObject.getLastStateChange())
            .waitEventType(sourceObject.getWaitEventType())
            .waitEventName(sourceObject.getWaitEventName())
            .lastQuery(sourceObject.getLastQuery())
            .backendTypeName(sourceObject.getBackendTypeName())
            .build();
    }
}
