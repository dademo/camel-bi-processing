/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository;

import fr.dademo.supervision.dependencies.entities.database.databaseview.DataBackendDatabaseSchemaViewStatisticsEntity;
import fr.dademo.supervision.dependencies.repositories.database.DataBackendDatabaseSchemaViewRepository;
import fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaViewView;
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
public interface ExtendedDataBackendDatabaseSchemaViewRepository extends DataBackendDatabaseSchemaViewRepository {

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaViewView( " +
        "       v, " +
        "       v.schema.id, " +
        "       COUNT(DISTINCT stats) " +
        "   ) " +
        "   FROM DataBackendDatabaseSchemaViewEntity v " +
        "   LEFT OUTER JOIN v.statistics stats " +
        "   WHERE v.schema.id = :schemaId " +
        "   GROUP BY v, v.schema.id " +
        "   ORDER BY v.id ASC")
    Page<DataBackendDatabaseSchemaViewView> findDatabasesSchemaViewsWithLinks(@Param("schemaId") Long schemaId, Pageable pageable);

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaViewView( " +
        "       v, " +
        "       v.schema.id, " +
        "       COUNT(DISTINCT stats) " +
        "   ) " +
        "   FROM DataBackendDatabaseSchemaViewEntity v " +
        "   LEFT OUTER JOIN v.statistics stats " +
        "   WHERE v.id = :id " +
        "   GROUP BY v, v.schema.id")
    Optional<DataBackendDatabaseSchemaViewView> findOneDatabaseSchemaViewWithLinks(@Param("id") Long id);

    @Query("SELECT s " +
        "   FROM DataBackendDatabaseSchemaViewStatisticsEntity s " +
        "   INNER JOIN FETCH s.backendStateExecution b " +
        "   WHERE s.view.id = :id " +
        "       AND b.timestamp BETWEEN :from and :to " +
        "   ORDER BY b.timestamp ASC")
    List<DataBackendDatabaseSchemaViewStatisticsEntity> findDatabaseSchemaViewStatisticsBetweenDates(
        @Param("id") Long id,
        @Param("from") Date from,
        @Param("to") Date to
    );

    @Query("SELECT s " +
        "   FROM DataBackendDatabaseSchemaViewStatisticsEntity s " +
        "   INNER JOIN s.backendStateExecution b " +
        "   WHERE s.view.id = :id " +
        "   AND b.timestamp = (" +
        "       SELECT MAX(b.timestamp) AS timestamp " +
        "       FROM DataBackendDatabaseSchemaViewStatisticsEntity s " +
        "       INNER JOIN s.backendStateExecution b " +
        "       WHERE s.view.id = :id " +
        ")")
    Optional<DataBackendDatabaseSchemaViewStatisticsEntity> findLatestDatabaseSchemaViewStatistic(@Param("id") Long id);
}
