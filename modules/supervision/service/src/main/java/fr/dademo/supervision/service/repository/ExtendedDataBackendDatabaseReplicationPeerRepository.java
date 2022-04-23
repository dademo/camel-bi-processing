/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository;

import fr.dademo.supervision.dependencies.entities.database.databasereplicationpeer.DataBackendDatabaseReplicationPeerStatisticsEntity;
import fr.dademo.supervision.dependencies.repositories.database.DataBackendDatabaseReplicationPeerRepository;
import fr.dademo.supervision.service.repository.views.DataBackendDatabaseReplicationPeerView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author dademo
 */
@Repository
public interface ExtendedDataBackendDatabaseReplicationPeerRepository extends DataBackendDatabaseReplicationPeerRepository {

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseReplicationPeerView( " +
        "       rp, " +
        "       rp.backendDescription.id, " +
        "       COUNT(DISTINCT stats) " +
        "   ) " +
        "   FROM DataBackendDatabaseReplicationPeerEntity rp " +
        "   LEFT OUTER JOIN rp.replicationPeerStatistics stats " +
        "   WHERE rp.backendDescription.id = :dataBackendId " +
        "   GROUP BY rp, rp.backendDescription.id " +
        "   ORDER BY rp.id ASC")
    Page<DataBackendDatabaseReplicationPeerView> findDatabaseReplicationPeersWithLinks(@Param("dataBackendId") Long dataBackendId, Pageable pageable);

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseReplicationPeerView( " +
        "       rp, " +
        "       rp.backendDescription.id, " +
        "       COUNT(DISTINCT stats) " +
        "   ) " +
        "   FROM DataBackendDatabaseReplicationPeerEntity rp " +
        "   LEFT OUTER JOIN rp.replicationPeerStatistics stats " +
        "   WHERE rp.id = :id " +
        "   GROUP BY rp, rp.backendDescription.id")
    Optional<DataBackendDatabaseReplicationPeerView> findOneDatabaseReplicationPeerWithLinks(@Param("id") Long id);

    @Query("SELECT s " +
        "   FROM DataBackendDatabaseReplicationPeerStatisticsEntity s " +
        "   INNER JOIN s.backendStateExecution b " +
        "   WHERE s.replicationPeer.id = :id " +
        "       AND b.timestamp BETWEEN :from and :to " +
        "   ORDER BY b.timestamp ASC")
    List<DataBackendDatabaseReplicationPeerStatisticsEntity> findDatabaseReplicationPeerStatisticsBetweenDates(
        @Param("id") Long id,
        @Param("from") Date from,
        @Param("to") Date to
    );

    @Query("SELECT s " +
        "   FROM DataBackendDatabaseReplicationPeerStatisticsEntity s " +
        "   INNER JOIN s.backendStateExecution b " +
        "   WHERE s.replicationPeer.id = :id" +
        "   AND b.timestamp = (" +
        "       SELECT MAX(b.timestamp) AS timestamp " +
        "       FROM DataBackendDatabaseReplicationPeerStatisticsEntity s " +
        "       INNER JOIN s.backendStateExecution b " +
        "       WHERE s.replicationPeer.id = :id " +
        ")")
    Optional<DataBackendDatabaseReplicationPeerStatisticsEntity> findLatestDatabaseReplicationPeerStatistic(@Param("id") Long id);
}
