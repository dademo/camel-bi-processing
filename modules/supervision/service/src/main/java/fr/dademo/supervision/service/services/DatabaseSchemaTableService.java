/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaTableDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaTableStatisticsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nonnull;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author dademo
 */
public interface DatabaseSchemaTableService {

    Page<DataBackendDatabaseSchemaTableDto> findTablesForDatabaseSchema(@Nonnull @Min(1) Long databaseSchemaId, @Nonnull Pageable pageable);

    Optional<DataBackendDatabaseSchemaTableDto> findDatabaseSchemaTableById(@Nonnull @Min(1) Long id);

    List<DataBackendDatabaseSchemaTableStatisticsDto> findDatabaseSchemaTableStatisticsBetween(
        @Nonnull @Min(1) Long id, @Nonnull Date from, @Nonnull Date to
    );

    Optional<DataBackendDatabaseSchemaTableStatisticsDto> findLatestDatabaseSchemaTableStatistics(@Nonnull @Min(1) Long id);
}
