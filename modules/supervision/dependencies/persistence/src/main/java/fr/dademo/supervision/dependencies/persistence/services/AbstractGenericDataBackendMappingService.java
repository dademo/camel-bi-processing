/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services;

import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescription;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendModuleMetaData;
import fr.dademo.supervision.dependencies.entities.DataBackendDescriptionEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendModuleMetaDataEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.dependencies.persistence.services.mappers.DataBackendDescriptionEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.DataBackendModuleMetaDataEntityMapper;
import fr.dademo.supervision.dependencies.repositories.DataBackendDescriptionRepository;
import fr.dademo.supervision.dependencies.repositories.DataBackendModuleMetaDataRepository;
import fr.dademo.supervision.dependencies.repositories.DatabaseBackendStateRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author dademo
 */
public abstract class AbstractGenericDataBackendMappingService {

    @Autowired
    private DatabaseBackendStateRepository databaseBackendStateRepository;

    @Autowired
    private DataBackendModuleMetaDataRepository dataBackendModuleMetaDataRepository;

    @Autowired
    private DataBackendDescriptionRepository dataBackendDescriptionRepository;


    protected DataBackendStateExecutionEntity mapCommonModuleDataToEntity(
        @Nonnull DataBackendModuleMetaData backendModuleMetaData,
        @Nonnull DataBackendDescription dataBackendDescription
    ) {
        return databaseBackendStateRepository.save(
            DataBackendStateExecutionEntity.builder()
                .timestamp(new Date(System.currentTimeMillis()))
                .dataBackendModuleMetaData(getDataBackendModuleMetaDataEntity(backendModuleMetaData))
                .dataBackendDescription(getDataBackendDescriptionEntity(dataBackendDescription))
                .build()
        );
    }

    private DataBackendModuleMetaDataEntity getDataBackendModuleMetaDataEntity(@Nonnull DataBackendModuleMetaData backendModuleMetaData) {

        final var defaultEntity = DataBackendModuleMetaDataEntityMapper.INSTANCE.toDataBackendModuleMetaDataEntity(
            backendModuleMetaData,
            new ArrayList<>()
        );
        return dataBackendModuleMetaDataRepository
            .findOne(Example.of(
                defaultEntity,
                ExampleMatcher.matching()
                    .withIgnorePaths(
                        "backendStateExecutions"
                    ))
            )
            .orElseGet(() -> dataBackendModuleMetaDataRepository.save(defaultEntity));
    }

    private DataBackendDescriptionEntity getDataBackendDescriptionEntity(@Nonnull DataBackendDescription dataBackendDescription) {

        final var defaultEntity = DataBackendDescriptionEntityMapper.INSTANCE.toDataBackendDescriptionEntity(
            dataBackendDescription,
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        );

        return dataBackendDescriptionRepository
            .findOneByPrimaryUrl(defaultEntity.getPrimaryUrl())
            .orElseGet(() -> dataBackendDescriptionRepository.save(defaultEntity));
    }
}
