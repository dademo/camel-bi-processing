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

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.model.database.resources;

import lombok.Builder;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.validation.constraints.Size;

/**
 * @author dademo
 */
@Data
@Builder
public class DatabaseNamespaceDefaultImpl implements DatabaseNamespace {

    @Nonnull
    @Size(min = 1)
    private String name;

    @Nonnull
    private Iterable<DatabaseTable> tables;

    @Nonnull
    private Iterable<DatabaseView> views;

    @Nonnull
    private Iterable<DatabaseView> indexes;
}
