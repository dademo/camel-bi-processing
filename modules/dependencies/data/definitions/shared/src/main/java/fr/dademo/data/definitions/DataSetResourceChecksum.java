/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
public interface DataSetResourceChecksum {

    @Nonnull
    String getType();

    @Nonnull
    String getValue();
}
