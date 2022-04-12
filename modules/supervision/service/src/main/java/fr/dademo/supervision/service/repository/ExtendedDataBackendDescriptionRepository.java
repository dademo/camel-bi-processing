/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository;

import fr.dademo.supervision.dependencies.repositories.DataBackendDescriptionRepository;
import fr.dademo.supervision.service.repository.views.DataBackendDescriptionView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author dademo
 */
@Repository
public interface ExtendedDataBackendDescriptionRepository extends DataBackendDescriptionRepository {

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDescriptionView( " +
        "       d, " +
        "       COUNT(DISTINCT exec), " +
        "       COUNT(DISTINCT db) " +
        "   ) " +
        "   FROM DataBackendDescriptionEntity d " +
        "   LEFT OUTER JOIN d.backendStateExecutions exec " +
        "   LEFT OUTER JOIN d.databases db " +
        "   GROUP BY d " +
        "   ORDER BY d.id ASC")
    Page<DataBackendDescriptionView> findDescriptionWithLinks(Pageable pageable);

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDescriptionView( " +
        "       d, " +
        "       COUNT(DISTINCT exec), " +
        "       COUNT(DISTINCT db) " +
        "   ) " +
        "   FROM DataBackendDescriptionEntity d " +
        "   LEFT OUTER JOIN d.backendStateExecutions exec " +
        "   LEFT OUTER JOIN d.databases db " +
        "   WHERE d.id = :id " +
        "   GROUP BY d")
    Optional<DataBackendDescriptionView> findOneDescriptionWithLinks(@Param("id") Long id);
}
