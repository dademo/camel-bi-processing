/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository;

import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseStatisticsEntity;
import fr.dademo.supervision.dependencies.repositories.database.DataBackendDatabaseRepository;
import fr.dademo.supervision.service.repository.views.DataBackendDatabaseView;
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
public interface ExtendedDataBackendDatabaseRepository extends DataBackendDatabaseRepository {

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseView( " +
        "       d, " +
        "       d.backendDescription.id, " +
        "       COUNT(DISTINCT stats), " +
        "       COUNT(DISTINCT schemas) " +
        "   ) " +
        "   FROM DataBackendDatabaseEntity d " +
        "   LEFT OUTER JOIN d.databaseStatistics stats " +
        "   LEFT OUTER JOIN d.schemas schemas " +
        "   WHERE d.backendDescription.id = :dataBackendId " +
        "   GROUP BY d, d.backendDescription.id " +
        "   ORDER BY d.id ASC")
    Page<DataBackendDatabaseView> findDatabasesWithLinks(@Param("dataBackendId") Long dataBackendId, Pageable pageable);

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseView( " +
        "       d, " +
        "       d.backendDescription.id, " +
        "       COUNT(DISTINCT stats), " +
        "       COUNT(DISTINCT schemas) " +
        "   ) " +
        "   FROM DataBackendDatabaseEntity d " +
        "   LEFT OUTER JOIN d.databaseStatistics stats " +
        "   LEFT OUTER JOIN d.schemas schemas " +
        "   WHERE d.id = :id " +
        "   GROUP BY d, d.backendDescription.id")
    Optional<DataBackendDatabaseView> findOneDatabaseWithLinks(@Param("id") Long id);

    @Query("SELECT s " +
        "   FROM DataBackendDatabaseStatisticsEntity s " +
        "   INNER JOIN FETCH s.backendStateExecution b " +
        "   WHERE s.database.id = :id " +
        "       AND b.timestamp BETWEEN :from and :to " +
        "   ORDER BY b.timestamp ASC")
    List<DataBackendDatabaseStatisticsEntity> findDatabaseStatisticsBetweenDates(
        @Param("id") Long id,
        @Param("from") Date from,
        @Param("to") Date to
    );

    @Query("SELECT s " +
        "   FROM DataBackendDatabaseStatisticsEntity s " +
        "   INNER JOIN s.backendStateExecution b " +
        "   WHERE s.database.id = :id" +
        "   AND b.timestamp = (" +
        "       SELECT MAX(b.timestamp) AS timestamp " +
        "       FROM DataBackendDatabaseStatisticsEntity s " +
        "       INNER JOIN s.backendStateExecution b " +
        "       WHERE s.database.id = :id " +
        ")")
    Optional<DataBackendDatabaseStatisticsEntity> findLatestDatabaseStatistic(@Param("id") Long id);
}
