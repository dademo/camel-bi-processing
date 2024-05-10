/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.repository.ExtendedDataBackendClusterNodeRepository;
import fr.dademo.supervision.service.services.dto.DataBackendClusterNodeDto;
import fr.dademo.supervision.service.services.mappers.DataBackendClusterNodeEntityToDtoMapper;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @author dademo
 */
@Service
public class DataBackendClusterNodeServiceImpl implements DataBackendClusterNodeService {

    @Autowired
    private ExtendedDataBackendClusterNodeRepository repository;

    @Override
    public List<DataBackendClusterNodeDto> findDataBackendClusterNodeByDataBackendId(
        @Nonnull @Min(1) Long dataBackendId, @Nonnull @Min(0) Long size) {

        return repository.findAllDataBackendClusterNodes(dataBackendId)
            .stream()
            .map(entity -> DataBackendClusterNodeEntityToDtoMapper.INSTANCE.viewToDto(
                entity,
                new ArrayList<>()
            ))
            .map(applyLatestStatisticsTimestampCount(size))
            .toList();
    }

    @Override
    public List<DataBackendClusterNodeDto> findDataBackendClusterNodeByDataBackendIdBetween(@Nonnull Long dataBackendId, @Nonnull Date from, @Nonnull Date to) {

        return repository.findAllDataBackendClusterNodes(dataBackendId)
            .stream()
            .map(entity -> DataBackendClusterNodeEntityToDtoMapper.INSTANCE.viewToDto(
                entity,
                new ArrayList<>()
            ))
            .map(applyStatisticsTimestampBetween(from, to))
            .toList();
    }

    @Override
    public List<DataBackendClusterNodeDto> findLatestDataBackendClusterNodesByDataBackendId(@Nonnull Long dataBackendId) {

        return repository.findLatestDataBackendClusterNodes(dataBackendId)
            .stream()
            .map(entity -> DataBackendClusterNodeEntityToDtoMapper.INSTANCE.viewToDto(
                entity,
                new ArrayList<>()
            ))
            .toList();
    }

    private Function<DataBackendClusterNodeDto, DataBackendClusterNodeDto> applyStatisticsTimestampBetween(@Nonnull Date from, @Nonnull Date to) {

        return entity -> entity.withTimestamps(
            repository.findDataBackendClusterNodesExecutionsBetweenDates(entity.getId(), from, to)
        );
    }

    private Function<DataBackendClusterNodeDto, DataBackendClusterNodeDto> applyLatestStatisticsTimestampCount(@Nonnull @Min(0) Long size) {

        return entity -> entity.withTimestamps(
            repository.findLatestDataBackendClusterNodesExecutions(entity.getId(), Pageable.ofSize(size.intValue()))
        );
    }
}
