/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.model.database.resources;

import lombok.Builder;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author dademo
 */
@Data
@Builder
public class DatabaseIndexDefaultImpl implements DatabaseIndex {

    @Nonnull
    @Size(min = 1)
    private String name;

    @Nonnull
    @Size(min = 1)
    private String tableName;

    @Nullable
    @Min(0)
    private Long indexScansCount;

    @Nullable
    @Min(0)
    private Long returnedRowsCount;

    @Nullable
    @Min(0)
    private Long fetchedRowsCount;
}
