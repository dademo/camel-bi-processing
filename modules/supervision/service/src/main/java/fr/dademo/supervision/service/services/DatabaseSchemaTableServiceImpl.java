/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.repository.ExtendedDataBackendDatabaseSchemaTableRepository;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaTableDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaTableStatisticsDto;
import fr.dademo.supervision.service.services.mappers.DataBackendDatabaseSchemaTableEntityToDtoMapper;
import fr.dademo.supervision.service.services.mappers.DataBackendDatabaseSchemaTableStatisticsEntityToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author dademo
 */
@Service
public class DatabaseSchemaTableServiceImpl implements DatabaseSchemaTableService {

    @Autowired
    private ExtendedDataBackendDatabaseSchemaTableRepository repository;

    @Override
    public Page<DataBackendDatabaseSchemaTableDto> findTablesForDatabaseSchema(@Nonnull Long databaseSchemaId, @Nonnull Pageable pageable) {

        return repository
            .findDatabasesSchemaTablesWithLinks(databaseSchemaId, pageable)
            .map(DataBackendDatabaseSchemaTableEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Optional<DataBackendDatabaseSchemaTableDto> findDatabaseSchemaTableById(@Nonnull Long id) {

        return repository
            .findOneDatabaseSchemaTableWithLinks(id)
            .map(DataBackendDatabaseSchemaTableEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public List<DataBackendDatabaseSchemaTableStatisticsDto> findDatabaseSchemaTableStatisticsBetween(
        @Nonnull Long id, @Nonnull Date from, @Nonnull Date to) {

        return repository
            .findDatabaseSchemaTableStatisticsBetweenDates(id, from, to)
            .stream()
            .map(DataBackendDatabaseSchemaTableStatisticsEntityToDtoMapper.INSTANCE::viewToDto)
            .toList();
    }

    @Override
    public Optional<DataBackendDatabaseSchemaTableStatisticsDto> findLatestDatabaseSchemaTableStatistics(@Nonnull Long id) {

        return repository
            .findLatestDatabaseSchemaTableStatistic(id)
            .map(DataBackendDatabaseSchemaTableStatisticsEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Boolean existsById(@Nonnull Long id) {
        return repository.existsById(id);
    }
}
