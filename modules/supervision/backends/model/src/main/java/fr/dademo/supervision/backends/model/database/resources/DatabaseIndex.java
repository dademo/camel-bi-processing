/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.model.database.resources;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author dademo
 */
public interface DatabaseIndex {

    @Nonnull
    @Size(min = 1)
    String getName();

    @Nonnull
    @Size(min = 1)
    String getTableName();

    @Nullable
    @Min(0)
    Long getIndexScansCount();

    @Nullable
    @Min(0)
    Long getReturnedRowsCount();

    @Nullable
    @Min(0)
    Long getFetchedRowsCount();
}
