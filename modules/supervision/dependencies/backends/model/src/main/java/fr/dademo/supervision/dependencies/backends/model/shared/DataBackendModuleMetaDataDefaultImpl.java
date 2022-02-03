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

package fr.dademo.supervision.dependencies.backends.model.shared;

import lombok.Builder;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Size;

/**
 * Data backend module meta data.
 *
 * @author dademo
 */
@Data
@Builder
public class DataBackendModuleMetaDataDefaultImpl implements DataBackendModuleMetaData {

    @Nonnull
    @Size(min = 1, max = 255)
    private String moduleName;

    @Nonnull
    @Size(min = 1, max = 255)
    private String moduleTitle;

    @Nonnull
    @Size(min = 1, max = 50)
    private String moduleVersion;

    @Nullable
    @Size(min = 1, max = 255)
    private String moduleVendor;
}
