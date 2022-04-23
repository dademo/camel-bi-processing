/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository;

import fr.dademo.supervision.dependencies.entities.DataBackendClusterNodeEntity;
import fr.dademo.supervision.dependencies.repositories.DataBackendClusterNodeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author dademo
 */
@Repository
public interface ExtendedDataBackendClusterNodeRepository extends DataBackendClusterNodeRepository {

    @Query("SELECT cn " +
        "   FROM DataBackendClusterNodeEntity cn " +
        "   WHERE cn.backendDescription.id = :backendDescriptionId " +
        "   ORDER BY cn.url ASC")
    List<DataBackendClusterNodeEntity> findAllDataBackendClusterNodes(@Param("backendDescriptionId") Long dataBackendId);

    @Query("SELECT b.timestamp " +
        "   FROM DataBackendClusterNodeEntity cn " +
        "   INNER JOIN cn.backendStateExecutions b " +
        "   WHERE cn.id = :clusterNodeEntityId " +
        "       AND b.timestamp BETWEEN :from and :to " +
        "   ORDER BY b.timestamp ASC")
    List<Date> findDataBackendClusterNodesExecutionsBetweenDates(
        @Param("clusterNodeEntityId") Long clusterNodeEntityId,
        @Param("from") Date from,
        @Param("to") Date to
    );

    @Query("SELECT b.timestamp " +
        "   FROM DataBackendClusterNodeEntity cn " +
        "   INNER JOIN cn.backendStateExecutions b " +
        "   WHERE cn.id = :clusterNodeEntityId " +
        "   ORDER BY b.timestamp ASC")
    List<Date> findLatestDataBackendClusterNodesExecutions(
        @Param("clusterNodeEntityId") Long clusterNodeEntityId,
        Pageable pageable
    );

    @Query("SELECT cn " +
        "   FROM DataBackendClusterNodeEntity cn " +
        "   INNER JOIN FETCH cn.backendStateExecutions b " +
        "   WHERE cn.backendDescription.id = :backendDescriptionId " +
        "   AND b.timestamp = (" +
        "       SELECT MAX(b.timestamp) AS timestamp " +
        "       FROM DataBackendClusterNodeEntity cn " +
        "       INNER JOIN cn.backendStateExecutions b " +
        "       WHERE cn.backendDescription.id = :id " +
        ")")
    List<DataBackendClusterNodeEntity> findLatestDataBackendClusterNodes(@Param("backendDescriptionId") Long dataBackendId);
}
