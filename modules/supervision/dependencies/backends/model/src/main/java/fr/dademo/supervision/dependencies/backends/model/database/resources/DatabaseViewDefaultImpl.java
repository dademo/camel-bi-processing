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
public class DatabaseViewDefaultImpl implements DatabaseView {

    @Nonnull
    @Size(min = 1, max = 255)
    private String name;

    @Nullable
    @Min(0)
    private Long rowsCount;

    @Nullable
    @Min(0)
    private Long totalSize;

    @Nullable
    private String expression;
}
