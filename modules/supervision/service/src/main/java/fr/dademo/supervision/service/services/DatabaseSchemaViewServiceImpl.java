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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dademo
 */
@Service
public class DatabaseSchemaViewServiceImpl implements DatabaseSchemaViewService {

    @Autowired
    private ExtendedDataBackendDatabaseSchemaViewRepository repository;

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
            .collect(Collectors.toList());
    }

    @Override
    public Optional<DataBackendDatabaseSchemaViewStatisticsDto> findLatestDatabaseSchemaViewStatistics(@Nonnull Long id) {

        return repository
            .findLatestDatabaseSchemaViewStatistic(id)
            .map(DataBackendDatabaseSchemaViewStatisticsEntityToDtoMapper.INSTANCE::viewToDto);
    }
}
