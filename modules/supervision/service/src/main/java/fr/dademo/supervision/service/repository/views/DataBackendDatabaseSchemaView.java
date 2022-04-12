/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository.views;

import fr.dademo.supervision.dependencies.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author dademo
 */
@Getter
@Builder
@AllArgsConstructor
public class DataBackendDatabaseSchemaView {

    private final DataBackendDatabaseSchemaEntity dataBackendDatabaseSchemaEntity;

    private final Long dataBackendDatabaseId;

    private final Long tablesCount;
    private final Long schemasCount;
    private final Long indexesCount;
}
