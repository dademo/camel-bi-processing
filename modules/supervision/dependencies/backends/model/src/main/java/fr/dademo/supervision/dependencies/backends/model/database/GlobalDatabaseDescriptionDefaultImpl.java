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
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescriptionDefaultImpl;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendKind;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class GlobalDatabaseDescriptionDefaultImpl extends DataBackendDescriptionDefaultImpl implements GlobalDatabaseDescription {

    @Nonnull
    private Iterable<DatabaseConnection> databaseConnections;

    @Nonnull
    private Iterable<DatabaseDescription> databasesDescriptions;

    @Nonnull
    @Override
    public final DataBackendKind getBackendKind() {
        return DataBackendKind.DATABASE;
    }
}
