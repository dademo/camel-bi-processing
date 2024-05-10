/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.repository.ExtendedDataBackendDatabaseSchemaIndexRepository;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaIndexDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaIndexStatisticsDto;
import fr.dademo.supervision.service.services.mappers.DataBackendDatabaseSchemaIndexEntityToDtoMapper;
import fr.dademo.supervision.service.services.mappers.DataBackendDatabaseSchemaIndexStatisticsEntityToDtoMapper;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author dademo
 */
@Service
public class DatabaseSchemaIndexServiceImpl implements DatabaseSchemaIndexService {

    @Autowired
    private ExtendedDataBackendDatabaseSchemaIndexRepository repository;

    @Override
    public Page<DataBackendDatabaseSchemaIndexDto> findIndexesForDatabaseSchema(@Nonnull Long databaseSchemaId, @Nonnull Pageable pageable) {

        return repository
            .findDatabasesSchemaIndexesWithLinks(databaseSchemaId, pageable)
            .map(DataBackendDatabaseSchemaIndexEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Optional<DataBackendDatabaseSchemaIndexDto> findDatabaseSchemaIndexById(@Nonnull Long id) {

        return repository
            .findOneDatabaseSchemaIndexWithLinks(id)
            .map(DataBackendDatabaseSchemaIndexEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public List<DataBackendDatabaseSchemaIndexStatisticsDto> findDatabaseSchemaIndexStatisticsBetween(
        @Nonnull Long id, @Nonnull Date from, @Nonnull Date to) {

        return repository
            .findDatabaseSchemaIndexStatisticsBetweenDates(id, from, to)
            .stream()
            .map(DataBackendDatabaseSchemaIndexStatisticsEntityToDtoMapper.INSTANCE::viewToDto)
            .toList();
    }

    @Override
    public Optional<DataBackendDatabaseSchemaIndexStatisticsDto> findLatestDatabaseSchemaIndexStatistics(@Nonnull Long id) {

        return repository
            .findLatestDatabaseSchemaIndexStatistic(id)
            .map(DataBackendDatabaseSchemaIndexStatisticsEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Boolean existsById(@Nonnull Long id) {
        return repository.existsById(id);
    }
}
