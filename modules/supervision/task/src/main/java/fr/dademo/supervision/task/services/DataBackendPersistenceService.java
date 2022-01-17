/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services;

import fr.dademo.supervision.backends.model.shared.DataBackendDescription;
import fr.dademo.supervision.backends.model.shared.DataBackendModuleMetaData;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
public interface DataBackendPersistenceService {

    void persistBackendFetchResult(
        @Nonnull DataBackendModuleMetaData backendModuleMetaData,
        @Nonnull DataBackendDescription dataBackendDescription
    );
}
