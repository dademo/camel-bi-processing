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

package fr.dademo.supervision.backends.model.shared;

import lombok.Builder;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.validation.constraints.Min;

/**
 * Data backend module meta data.
 *
 * @author dademo
 */
@Data
@Builder
public class DataBackendModuleMetaDataDefaultImpl implements DataBackendModuleMetaData {

    @Nonnull
    @Min(1)
    private String collectionModuleName;

    @Nonnull
    @Min(1)
    private String collectionModuleVersion;
}
