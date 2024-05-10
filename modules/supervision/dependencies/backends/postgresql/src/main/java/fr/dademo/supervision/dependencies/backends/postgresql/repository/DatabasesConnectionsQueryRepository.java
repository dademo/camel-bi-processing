/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.repository;

import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseConnectionEntity;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * @author dademo
 */
public interface DatabasesConnectionsQueryRepository {

    @Nonnull
    List<DatabaseConnectionEntity> getDatabasesConnections();
}
