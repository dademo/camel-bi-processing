/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.repositories;

import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dademo
 */
@Repository
public interface DatabaseBackendStateRepository extends JpaRepository<DataBackendStateExecutionEntity, Long> {
}
