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

package fr.dademo.supervision.task.repositories.database;

import fr.dademo.supervision.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author dademo
 */
public interface DataBackendDatabaseSchemaRepository extends
    JpaRepository<DataBackendDatabaseSchemaEntity, Long>,
    JpaSpecificationExecutor<DataBackendDatabaseSchemaEntity> {
}
