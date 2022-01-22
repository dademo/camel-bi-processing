/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.model.shared;

import javax.annotation.Nonnull;
import javax.validation.constraints.Size;

/**
 * Data backend module meta data.
 *
 * @author dademo
 */
public interface DataBackendModuleMetaData {

    /**
     * Get this module name.
     *
     * @return this module name
     */
    @Nonnull
    @Size(min = 1, max = 255)
    String getModuleName();

    /**
     * Get this module name.
     *
     * @return this module name
     */
    @Nonnull
    @Size(min = 1, max = 255)
    String getModuleTitle();

    /**
     * Get this module version.
     *
     * @return this module version
     */
    @Nonnull
    @Size(min = 1, max = 50)
    String getModuleVersion();

    /**
     * Get this module vendor.
     *
     * @return this module vendor
     */
    @Nonnull
    @Size(min = 1, max = 255)
    String getModuleVendor();
}
