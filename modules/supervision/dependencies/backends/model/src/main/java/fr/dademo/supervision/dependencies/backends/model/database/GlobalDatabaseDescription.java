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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseConnection;
import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseConnectionDefaultImpl;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescription;
import lombok.NonNull;

import java.util.List;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface GlobalDatabaseDescription extends DataBackendDescription {

    @NonNull
    @JsonDeserialize(as = List.class, contentAs = DatabaseConnectionDefaultImpl.class)
    Iterable<DatabaseConnection> getDatabaseConnections();

    @NonNull
    @JsonDeserialize(as = List.class, contentAs = DatabaseDescriptionDefaultImpl.class)
    Iterable<DatabaseDescription> getDatabasesDescriptions();
}
