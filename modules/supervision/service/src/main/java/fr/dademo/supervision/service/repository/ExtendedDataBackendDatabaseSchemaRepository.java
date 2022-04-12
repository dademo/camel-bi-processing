/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository;

import fr.dademo.supervision.dependencies.repositories.database.DataBackendDatabaseSchemaRepository;
import fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaView;
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
public interface ExtendedDataBackendDatabaseSchemaRepository extends DataBackendDatabaseSchemaRepository {

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaView( " +
        "       s, " +
        "       s.database.id, " +
        "       COUNT(DISTINCT tables), " +
        "       COUNT(DISTINCT views), " +
        "       COUNT(DISTINCT indexes) " +
        "   ) " +
        "   FROM DataBackendDatabaseSchemaEntity s " +
        "   LEFT OUTER JOIN s.tables tables " +
        "   LEFT OUTER JOIN s.views views " +
        "   LEFT OUTER JOIN s.indexes indexes " +
        "   WHERE s.database.id = :databaseId " +
        "   GROUP BY s, s.database.id " +
        "   ORDER BY s.id ASC")
    Page<DataBackendDatabaseSchemaView> findDatabasesSchemasWithLinks(@Param("databaseId") Long databaseId, Pageable pageable);

    @Query("SELECT new fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaView( " +
        "       s, " +
        "       s.database.id, " +
        "       COUNT(DISTINCT tables), " +
        "       COUNT(DISTINCT views), " +
        "       COUNT(DISTINCT indexes) " +
        "   ) " +
        "   FROM DataBackendDatabaseSchemaEntity s " +
        "   LEFT OUTER JOIN s.tables tables " +
        "   LEFT OUTER JOIN s.views views " +
        "   LEFT OUTER JOIN s.indexes indexes " +
        "   WHERE s.id = :id " +
        "   GROUP BY s, s.database.id")
    Optional<DataBackendDatabaseSchemaView> findOneDatabaseSchemaWithLinks(@Param("id") Long id);
}
