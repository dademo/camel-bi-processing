/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.repository.ExtendedDataBackendDatabaseSchemaViewRepository;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaViewDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaViewStatisticsDto;
import fr.dademo.supervision.service.services.mappers.DataBackendDatabaseSchemaViewEntityToDtoMapper;
import fr.dademo.supervision.service.services.mappers.DataBackendDatabaseSchemaViewStatisticsEntityToDtoMapper;
import jakarta.annotation.Nonnull;
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
public class DatabaseSchemaViewServiceImpl implements DatabaseSchemaViewService {

    private final ExtendedDataBackendDatabaseSchemaViewRepository repository;

    public DatabaseSchemaViewServiceImpl(@Nonnull ExtendedDataBackendDatabaseSchemaViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<DataBackendDatabaseSchemaViewDto> findViewsForDatabaseSchema(@Nonnull Long databaseSchemaId, @Nonnull Pageable pageable) {

        return repository
            .findDatabasesSchemaViewsWithLinks(databaseSchemaId, pageable)
            .map(DataBackendDatabaseSchemaViewEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Optional<DataBackendDatabaseSchemaViewDto> findDatabaseSchemaViewById(@Nonnull Long id) {

        return repository
            .findOneDatabaseSchemaViewWithLinks(id)
            .map(DataBackendDatabaseSchemaViewEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public List<DataBackendDatabaseSchemaViewStatisticsDto> findDatabaseSchemaViewStatisticsBetween(
        @Nonnull Long id, @Nonnull Date from, @Nonnull Date to) {

        return repository
            .findDatabaseSchemaViewStatisticsBetweenDates(id, from, to)
            .stream()
            .map(DataBackendDatabaseSchemaViewStatisticsEntityToDtoMapper.INSTANCE::viewToDto)
            .toList();
    }

    @Override
    public Optional<DataBackendDatabaseSchemaViewStatisticsDto> findLatestDatabaseSchemaViewStatistics(@Nonnull Long id) {

        return repository
            .findLatestDatabaseSchemaViewStatistic(id)
            .map(DataBackendDatabaseSchemaViewStatisticsEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Boolean existsById(@Nonnull Long id) {
        return repository.existsById(id);
    }
}
