/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.database.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.net.InetAddress;
import java.util.Date;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DatabaseConnection {

    @Nullable
    DatabaseConnectionState getConnectionState();

    @Nullable
    @Min(1)
    Long getConnectionPID();

    @Nullable
    @Size(min = 1, max = 255)
    String getConnectedDatabaseName();

    @Nullable
    @Size(min = 1, max = 255)
    String getUserName();

    @Nullable
    @Size(min = 1, max = 255)
    String getApplicationName();

    @Nullable
    InetAddress getClientAddress();

    @Nullable
    @Size(min = 1, max = 255)
    String getClientHostname();

    @Nullable
    @Min(1)
    Long getClientPort();

    @Nullable
    Date getConnectionStart();

    @Nullable
    Date getTransactionStart();

    @Nullable
    Date getLastQueryStart();

    @Nullable
    Date getLastStateChange();

    @Nullable
    @Size(min = 1)
    String getWaitEventType();

    @Nullable
    @Size(min = 1)
    String getWaitEventName();

    @Nullable
    @Size(min = 1)
    String getLastQuery();

    @Nullable
    @Size(min = 1)
    String getBackendTypeName();
}
