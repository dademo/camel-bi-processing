/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.repository.ExtendedDataBackendDatabaseReplicationPeerRepository;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseReplicationPeerDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseReplicationPeerStatisticsDto;
import fr.dademo.supervision.service.services.mappers.DataBackendDatabaseReplicationPeerEntityToDtoMapper;
import fr.dademo.supervision.service.services.mappers.DataBackendDatabaseReplicationPeerStatisticsEntityToDtoMapper;
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
public class DatabaseReplicationPeerServiceImpl implements DatabaseReplicationPeerService {

    @Autowired
    private ExtendedDataBackendDatabaseReplicationPeerRepository repository;

    @Override
    public Page<DataBackendDatabaseReplicationPeerDto> findDatabaseReplicationPeersForDataBackend(@Nonnull Long dataBackendId, @Nonnull Pageable pageable) {

        return repository.findDatabaseReplicationPeersWithLinks(dataBackendId, pageable)
            .map(DataBackendDatabaseReplicationPeerEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Optional<DataBackendDatabaseReplicationPeerDto> findDatabaseReplicationPeerById(@Nonnull Long id) {

        return repository.findOneDatabaseReplicationPeerWithLinks(id)
            .map(DataBackendDatabaseReplicationPeerEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public List<DataBackendDatabaseReplicationPeerStatisticsDto> findDatabaseReplicationPeerStatisticsBetween(@Nonnull Long id, @Nonnull Date from, @Nonnull Date to) {

        return repository.findDatabaseReplicationPeerStatisticsBetweenDates(id, from, to)
            .stream().map(DataBackendDatabaseReplicationPeerStatisticsEntityToDtoMapper.INSTANCE::viewToDto)
            .toList();
    }

    @Override
    public Optional<DataBackendDatabaseReplicationPeerStatisticsDto> findLatestDatabaseReplicationPeerStatistics(@Nonnull Long id) {

        return repository.findLatestDatabaseReplicationPeerStatistic(id)
            .map(DataBackendDatabaseReplicationPeerStatisticsEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Boolean existsById(@Nonnull Long id) {
        return repository.existsById(id);
    }
}
