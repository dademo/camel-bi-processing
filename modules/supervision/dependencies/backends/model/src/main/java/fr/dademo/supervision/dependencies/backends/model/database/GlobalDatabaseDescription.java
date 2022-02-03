/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.database;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseConnection;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescription;
import lombok.NonNull;

/**
 * @author dademo
 */
public interface GlobalDatabaseDescription extends DataBackendDescription {

    @NonNull
    Iterable<DatabaseConnection> getDatabaseConnections();

    @NonNull
    Iterable<DatabaseDescription> getDatabasesDescriptions();
}
