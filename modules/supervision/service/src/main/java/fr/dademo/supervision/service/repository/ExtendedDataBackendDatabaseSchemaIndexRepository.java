/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository;

import fr.dademo.supervision.dependencies.entities.database.databaseindex.DataBackendDatabaseSchemaIndexStatisticsEntity;
import fr.dademo.supervision.dependencies.repositories.database.DataBackendDatabaseSchemaIndexRepository;
import fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaIndexView;
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
public interface ExtendedDataBackendDatabaseSchemaIndexRepository extends DataBackendDatabaseSchemaIndexRepository {

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaIndexView( " +
        "       i, " +
        "       i.schema.id, " +
        "       COUNT(DISTINCT stats) " +
        "   ) " +
        "   FROM DataBackendDatabaseSchemaIndexEntity i " +
        "   LEFT OUTER JOIN i.statistics stats " +
        "   WHERE i.schema.id = :schemaId " +
        "   GROUP BY i, i.schema.id " +
        "   ORDER BY i.id ASC")
    Page<DataBackendDatabaseSchemaIndexView> findDatabasesSchemaIndexesWithLinks(@Param("schemaId") Long schemaId, Pageable pageable);

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaIndexView( " +
        "       i, " +
        "       i.schema.id, " +
        "       COUNT(DISTINCT stats) " +
        "   ) " +
        "   FROM DataBackendDatabaseSchemaIndexEntity i " +
        "   LEFT OUTER JOIN i.statistics stats " +
        "   WHERE i.id = :id " +
        "   GROUP BY i, i.schema.id")
    Optional<DataBackendDatabaseSchemaIndexView> findOneDatabaseSchemaIndexWithLinks(@Param("id") Long id);

    @Query("SELECT s " +
        "   FROM DataBackendDatabaseSchemaIndexStatisticsEntity s " +
        "   INNER JOIN FETCH s.backendStateExecution b " +
        "   WHERE s.index.id = :id " +
        "       AND b.timestamp BETWEEN :from and :to " +
        "   ORDER BY b.timestamp ASC")
    List<DataBackendDatabaseSchemaIndexStatisticsEntity> findDatabaseSchemaIndexStatisticsBetweenDates(
        @Param("id") Long id,
        @Param("from") Date from,
        @Param("to") Date to
    );

    @Query("SELECT s " +
        "   FROM DataBackendDatabaseSchemaIndexStatisticsEntity s " +
        "   INNER JOIN s.backendStateExecution b " +
        "   WHERE s.index.id = :id" +
        "   AND b.timestamp = (" +
        "       SELECT MAX(b.timestamp) AS timestamp " +
        "       FROM DataBackendDatabaseSchemaIndexStatisticsEntity s " +
        "       INNER JOIN s.backendStateExecution b " +
        "       WHERE s.index.id = :id " +
        ")")
    Optional<DataBackendDatabaseSchemaIndexStatisticsEntity> findLatestDatabaseSchemaIndexStatistic(@Param("id") Long id);
}
