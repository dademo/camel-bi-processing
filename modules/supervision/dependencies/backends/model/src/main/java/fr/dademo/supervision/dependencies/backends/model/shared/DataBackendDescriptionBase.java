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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DataBackendDescriptionBase {

    /**
     * The data size.
     *
     * @return the data size
     */
    @Nullable
    @Min(0)
    Long getSizeBytes();

    /**
     * Get the effective data size (space really used on the underlying file system).
     *
     * @return the effective data size
     */
    @Nullable
    @Min(0)
    Long getEffectiveSizeBytes();

    /**
     * Get the available size for the data backend.
     *
     * @return the available size for the data backend
     */
    @Nullable
    @Min(0)
    Long getAvailableSizeBytes();
}
