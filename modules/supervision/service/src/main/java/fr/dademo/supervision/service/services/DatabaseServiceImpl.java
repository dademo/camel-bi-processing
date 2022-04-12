/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.repository.ExtendedDataBackendDatabaseRepository;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseDto;
import fr.dademo.supervision.service.services.mappers.DataBackendDatabaseEntityToDtoMapper;
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
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private ExtendedDataBackendDatabaseRepository repository;

    @Override
    public Page<DataBackendDatabaseDto> findDatabasesForDataBackend(@Nonnull Long dataBackendId, @Nonnull Pageable pageable) {

        return repository.findDatabasesWithLinks(dataBackendId, pageable)
            .map(DataBackendDatabaseEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Optional<DataBackendDatabaseDto> findDatabaseById(@Nonnull Long id) {

        return repository.findOneDatabaseWithLinks(id)
            .map(DataBackendDatabaseEntityToDtoMapper.INSTANCE::viewToDto);
    }
}
