/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.repository.ExtendedDataBackendDatabaseSchemaRepository;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaDto;
import fr.dademo.supervision.service.services.mappers.DataBackendDatabaseSchemaEntityToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author dademo
 */
@Service
public class DatabaseSchemaServiceImpl implements DatabaseSchemaService {

    @Autowired
    private ExtendedDataBackendDatabaseSchemaRepository repository;

    @Override
    public Page<DataBackendDatabaseSchemaDto> findSchemasForDatabase(@Nonnull Long databaseId, @Nonnull Pageable pageable) {

        return repository
            .findDatabasesSchemasWithLinks(databaseId, pageable)
            .map(DataBackendDatabaseSchemaEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Optional<DataBackendDatabaseSchemaDto> findSchemaById(@Nonnull Long id) {

        return repository
            .findOneDatabaseSchemaWithLinks(id)
            .map(DataBackendDatabaseSchemaEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Boolean existsById(@Nonnull Long id) {
        return repository.existsById(id);
    }
}
