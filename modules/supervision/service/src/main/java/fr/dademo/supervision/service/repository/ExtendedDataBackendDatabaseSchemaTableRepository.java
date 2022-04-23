/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository;

import fr.dademo.supervision.dependencies.entities.database.databasetable.DataBackendDatabaseSchemaTableStatisticsEntity;
import fr.dademo.supervision.dependencies.repositories.database.DataBackendDatabaseSchemaTableRepository;
import fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaTableView;
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
public interface ExtendedDataBackendDatabaseSchemaTableRepository extends DataBackendDatabaseSchemaTableRepository {

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaTableView( " +
        "       t, " +
        "       t.schema.id, " +
        "       COUNT(DISTINCT stats) " +
        "   ) " +
        "   FROM DataBackendDatabaseSchemaTableEntity t " +
        "   LEFT OUTER JOIN t.statistics stats " +
        "   WHERE t.schema.id = :schemaId " +
        "   GROUP BY t, t.schema.id " +
        "   ORDER BY t.id ASC")
    Page<DataBackendDatabaseSchemaTableView> findDatabasesSchemaTablesWithLinks(@Param("schemaId") Long schemaId, Pageable pageable);

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaTableView( " +
        "       t, " +
        "       t.schema.id, " +
        "       COUNT(DISTINCT stats) " +
        "   ) " +
        "   FROM DataBackendDatabaseSchemaTableEntity t " +
        "   LEFT OUTER JOIN t.statistics stats " +
        "   WHERE t.id = :id " +
        "   GROUP BY t, t.schema.id")
    Optional<DataBackendDatabaseSchemaTableView> findOneDatabaseSchemaTableWithLinks(@Param("id") Long id);

    @Query("SELECT s " +
        "   FROM DataBackendDatabaseSchemaTableStatisticsEntity s " +
        "   INNER JOIN FETCH s.backendStateExecution b " +
        "   WHERE s.table.id = :id " +
        "       AND b.timestamp BETWEEN :from and :to " +
        "   ORDER BY b.timestamp ASC")
    List<DataBackendDatabaseSchemaTableStatisticsEntity> findDatabaseSchemaTableStatisticsBetweenDates(
        @Param("id") Long id,
        @Param("from") Date from,
        @Param("to") Date to
    );

    @Query("SELECT s " +
        "   FROM DataBackendDatabaseSchemaTableStatisticsEntity s " +
        "   INNER JOIN s.backendStateExecution b " +
        "   WHERE s.table.id = :id " +
        "   AND b.timestamp = (" +
        "       SELECT MAX(b.timestamp) AS timestamp " +
        "       FROM DataBackendDatabaseSchemaTableStatisticsEntity s " +
        "       INNER JOIN s.backendStateExecution b " +
        "       WHERE s.table.id = :id " +
        ")")
    Optional<DataBackendDatabaseSchemaTableStatisticsEntity> findLatestDatabaseSchemaTableStatistic(@Param("id") Long id);
}
