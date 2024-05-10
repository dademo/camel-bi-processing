/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaDto;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * @author dademo
 */
public interface DatabaseSchemaService {

    Page<DataBackendDatabaseSchemaDto> findSchemasForDatabase(@Nonnull @Min(1) Long databaseId, @Nonnull Pageable pageable);

    Optional<DataBackendDatabaseSchemaDto> findSchemaById(@Nonnull @Min(1) Long id);

    Boolean existsById(@Nonnull @Min(1) Long id);
}
