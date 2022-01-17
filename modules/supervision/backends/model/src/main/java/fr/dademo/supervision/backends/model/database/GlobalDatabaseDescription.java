/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.model.database;

import fr.dademo.supervision.backends.model.database.resources.DatabaseConnection;
import fr.dademo.supervision.backends.model.shared.DataBackendDescription;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * @author dademo
 */
public interface GlobalDatabaseDescription extends DataBackendDescription {

    @Nullable
    Date getStartTime();

    @Nullable
    @Min(0)
    Long getMemoryUsageBytes();

    @Nullable
    @Min(0)
    Long getCpuUsageMilliCPU();

    @NonNull
    Iterable<DatabaseConnection> getDatabaseConnections();

    @NonNull
    Iterable<DatabaseDescription> getDatabasesDescriptions();
}
