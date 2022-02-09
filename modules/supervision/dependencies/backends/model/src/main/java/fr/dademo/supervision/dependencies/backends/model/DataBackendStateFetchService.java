/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model;

import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescription;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendModuleMetaData;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@Service
public interface DataBackendStateFetchService {

    @Nonnull
    DataBackendModuleMetaData getModuleMetaData();

    @Nonnull
    DataBackendDescription getDataBackendDescription();

    @Nonnull
    DataBackendDescription getDataBackendDescriptionFull();
}
