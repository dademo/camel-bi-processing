/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.model.shared;

import javax.annotation.Nonnull;
import javax.validation.constraints.Min;

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
    @Min(1)
    String getCollectionModuleName();

    /**
     * Get this module name.
     *
     * @return this module name
     */
    @Nonnull
    @Min(1)
    String getCollectionModuleTitle();

    /**
     * Get this module version.
     *
     * @return this module version
     */
    @Nonnull
    @Min(1)
    String getCollectionModuleVersion();

    /**
     * Get this module vendor.
     *
     * @return this module vendor
     */
    @Nonnull
    @Min(1)
    String getCollectionModuleVendor();
}
