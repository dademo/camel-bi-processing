/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.repositories;

import fr.dademo.supervision.entities.DataBackendModuleMetaDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author dademo
 */
public interface DataBackendModuleMetaDataRepository extends
    JpaRepository<DataBackendModuleMetaDataEntity, Long>,
    JpaSpecificationExecutor<DataBackendModuleMetaDataEntity> {
}
