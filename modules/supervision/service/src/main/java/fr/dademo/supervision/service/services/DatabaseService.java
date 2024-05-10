/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.services.dto.DataBackendDatabaseDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseStatisticsDto;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author dademo
 */
public interface DatabaseService {

    Page<DataBackendDatabaseDto> findDatabasesForDataBackend(@Nonnull @Min(1) Long dataBackendId, @Nonnull Pageable pageable);

    Optional<DataBackendDatabaseDto> findDatabaseById(@Nonnull @Min(1) Long id);

    List<DataBackendDatabaseStatisticsDto> findDatabaseStatisticsBetween(
        @Nonnull @Min(1) Long id, @Nonnull Date from, @Nonnull Date to
    );

    Optional<DataBackendDatabaseStatisticsDto> findLatestDatabaseStatistics(@Nonnull @Min(1) Long id);

    Boolean existsById(@Nonnull @Min(1) Long id);

}
