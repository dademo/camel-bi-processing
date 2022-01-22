/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services;

import fr.dademo.supervision.backends.model.database.GlobalDatabaseDescription;
import fr.dademo.supervision.backends.model.shared.DataBackendModuleMetaData;
import fr.dademo.supervision.entities.DataBackendStateExecutionEntity;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
public interface DatabaseBackendMappingService {

    @Nonnull
    DataBackendStateExecutionEntity mapModuleDataToEntity(
        @Nonnull DataBackendModuleMetaData backendModuleMetaData,
        @Nonnull GlobalDatabaseDescription globalDatabaseDescription);
}
