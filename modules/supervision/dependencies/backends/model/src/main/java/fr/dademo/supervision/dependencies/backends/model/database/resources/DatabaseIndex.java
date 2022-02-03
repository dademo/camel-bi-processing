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

package fr.dademo.supervision.dependencies.backends.model.database.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DatabaseIndex {

    @Nonnull
    @Size(min = 1, max = 255)
    String getName();

    @Nonnull
    @Size(min = 1, max = 255)
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
