/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.repository;

import fr.dademo.supervision.backends.postgresql.repository.entities.DatabaseTableRowsCountEntity;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
public interface DatabaseTableRowsCountQueryRepository {

    @Nonnull
    DatabaseTableRowsCountEntity getRowCountForTable(@Nonnull String tableSchema, @Nonnull String tableName);
}
