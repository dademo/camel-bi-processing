/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repositories;

import fr.dademo.supervision.entities.DataBackendStateExecutionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dademo
 */
@Repository
public interface DatabaseQueryRepository extends PagingAndSortingRepository<DataBackendStateExecutionEntity, Long> {

    List<String> findAllDistinctDataBackendDescriptionBackendName();
}
