/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.model.database.resources;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.net.InetAddress;
import java.util.Date;

/**
 * @author dademo
 */
public interface DatabaseConnection {

    @Nullable
    @Size(min = 1)
    DatabaseConnectionState getConnectionState();

    @Nullable
    @Min(1)
    Long getConnectionPID();

    @Nullable
    @Size(min = 1)
    String getConnectedDatabaseName();

    @Nullable
    @Size(min = 1)
    String getUserName();

    @Nullable
    @Size(min = 1)
    String getApplicationName();

    @Nullable
    InetAddress getClientAddress();

    @Nullable
    @Size(min = 1)
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
