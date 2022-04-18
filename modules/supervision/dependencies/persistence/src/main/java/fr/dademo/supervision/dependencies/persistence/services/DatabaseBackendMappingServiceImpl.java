/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services;

import fr.dademo.supervision.dependencies.backends.model.database.DatabaseDescription;
import fr.dademo.supervision.dependencies.backends.model.database.GlobalDatabaseDescription;
import fr.dademo.supervision.dependencies.backends.model.database.resources.*;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendModuleMetaData;
import fr.dademo.supervision.dependencies.entities.DataBackendDescriptionEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseindex.DataBackendDatabaseSchemaIndexEntity;
import fr.dademo.supervision.dependencies.entities.database.databasereplicationpeer.DataBackendDatabaseReplicationPeerEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
import fr.dademo.supervision.dependencies.entities.database.databasetable.DataBackendDatabaseSchemaTableEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseview.DataBackendDatabaseSchemaViewEntity;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.connection.DataBackendDatabaseConnectionEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.database.DataBackendDatabaseEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.database.DataBackendDatabaseStatisticsEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.databaseindex.DataBackendDatabaseSchemaIndexEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.databaseindex.DataBackendDatabaseSchemaIndexStatisticsEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.databaseschema.DataBackendDatabaseSchemaEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.databasetable.DataBackendDatabaseSchemaTableEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.databasetable.DataBackendDatabaseSchemaTableStatisticsEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.databaseview.DataBackendDatabaseSchemaViewEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.databaseview.DataBackendDatabaseSchemaViewEntityStatisticsMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.replicationpeer.DataBackendDatabaseReplicationPeerEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.mappers.database.replicationpeer.DataBackendDatabaseReplicationPeerStatisticsEntityMapper;
import fr.dademo.supervision.dependencies.persistence.services.specialSpecifications.DataBackendDatabaseEntitySpecification;
import fr.dademo.supervision.dependencies.repositories.DataBackendReplicationPeerRepository;
import fr.dademo.supervision.dependencies.repositories.DatabaseBackendStateRepository;
import fr.dademo.supervision.dependencies.repositories.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author dademo
 */
@Service
public class DatabaseBackendMappingServiceImpl extends AbstractGenericDataBackendMappingService implements DatabaseBackendMappingService {

    @Autowired
    private DatabaseBackendStateRepository databaseBackendStateRepository;

    @Autowired
    private DataBackendDatabaseRepository dataBackendDatabaseRepository;

    @Autowired
    private DataBackendReplicationPeerRepository dataBackendReplicationPeerRepository;

    @Autowired
    private DataBackendDatabaseSchemaRepository dataBackendDatabaseSchemaRepository;

    @Autowired
    private DataBackendDatabaseSchemaTableRepository dataBackendDatabaseSchemaTableRepository;

    @Autowired
    private DataBackendDatabaseSchemaViewRepository dataBackendDatabaseSchemaViewRepository;

    @Autowired
    private DataBackendDatabaseSchemaIndexRepository dataBackendDatabaseSchemaIndexRepository;


    @Nonnull
    @Override
    public DataBackendStateExecutionEntity mapModuleDataToDatabaseEntity(
        @Nonnull DataBackendModuleMetaData backendModuleMetaData,
        @Nonnull GlobalDatabaseDescription globalDatabaseDescription) {

        final var backendStateExecutionEntity = mapCommonModuleDataToEntity(
            backendModuleMetaData,
            globalDatabaseDescription
        );

        databaseBackendStateRepository.save(backendStateExecutionEntity);

        // Applying database-related values
        applyDatabaseValues(
            backendStateExecutionEntity,
            globalDatabaseDescription
        );

        return backendStateExecutionEntity;
    }

    private void applyDatabaseValues(DataBackendStateExecutionEntity dataBackendStateExecution,
                                     GlobalDatabaseDescription globalDatabaseDescription) {

        var dataBackendDescription = dataBackendStateExecution.getDataBackendDescription();

        dataBackendDescription.setDatabases(
            StreamSupport.stream(globalDatabaseDescription.getDatabasesDescriptions().spliterator(), false)
                .map(databaseDescription -> mapToDataBackendDatabaseDescriptionEntity(
                    databaseDescription,
                    dataBackendDescription,
                    dataBackendStateExecution
                ))
                .collect(Collectors.toList())
        );

        dataBackendDescription.getDatabaseConnections().addAll(
            StreamSupport.stream(globalDatabaseDescription.getDatabaseConnections().spliterator(), false)
                .map(databaseConnection -> DataBackendDatabaseConnectionEntityMapper.INSTANCE.toDataBackendDatabaseConnectionEntity(
                    databaseConnection,
                    dataBackendDescription,
                    dataBackendStateExecution
                ))
                .collect(Collectors.toList())
        );

        dataBackendDescription.getDatabaseReplicationPeers().addAll(
            StreamSupport.stream(globalDatabaseDescription.getDatabaseReplicationPeers().spliterator(), false)
                .map(databaseReplicationPeer -> mapToDataBackendDatabaseReplicationPeerEntity(
                    databaseReplicationPeer,
                    dataBackendDescription,
                    dataBackendStateExecution
                ))
                .collect(Collectors.toList())
        );
    }

    private DataBackendDatabaseEntity mapToDataBackendDatabaseDescriptionEntity(DatabaseDescription databaseDescription,
                                                                                DataBackendDescriptionEntity backendDescription,
                                                                                DataBackendStateExecutionEntity dataBackendStateExecution) {

        final var databaseDescriptionEntity = DataBackendDatabaseEntityMapper.INSTANCE.toDataBackendDatabaseEntity(
            databaseDescription,
            backendDescription,
            new ArrayList<>(),
            new ArrayList<>()
        );

        final var finalDatabaseDescriptionEntity = dataBackendDatabaseRepository
            .findOne(DataBackendDatabaseEntitySpecification.forEntity(databaseDescriptionEntity))
            .orElseGet(() -> dataBackendDatabaseRepository.save(databaseDescriptionEntity));

        finalDatabaseDescriptionEntity.getDatabaseStatistics().add(
            DataBackendDatabaseStatisticsEntityMapper.INSTANCE.toDataBackendDatabaseStatisticsEntity(
                databaseDescription,
                finalDatabaseDescriptionEntity,
                dataBackendStateExecution
            )
        );

        dataBackendDatabaseRepository.save(finalDatabaseDescriptionEntity);

        finalDatabaseDescriptionEntity.setSchemas(
            StreamSupport.stream(databaseDescription.getDatabaseSchemas().spliterator(), false)
                .map(databaseSchema -> mapToDataBackendDatabaseSchemaEntity(databaseSchema, finalDatabaseDescriptionEntity, dataBackendStateExecution))
                .collect(Collectors.toList())
        );

        return finalDatabaseDescriptionEntity;
    }

    private DataBackendDatabaseReplicationPeerEntity mapToDataBackendDatabaseReplicationPeerEntity(DatabaseReplicationPeer databaseDescriptionPeer,
                                                                                                   DataBackendDescriptionEntity backendDescription,
                                                                                                   DataBackendStateExecutionEntity dataBackendStateExecution) {

        final var databaseReplicationPeerEntity = DataBackendDatabaseReplicationPeerEntityMapper.INSTANCE.toDataBackendDatabaseReplicationPeerEntity(
            databaseDescriptionPeer,
            backendDescription,
            new ArrayList<>()
        );

        final var finalDatabaseDescriptionEntity = dataBackendReplicationPeerRepository
            .findOne(Example.of(
                databaseReplicationPeerEntity,
                ExampleMatcher.matching()
                    .withIgnorePaths(
                        "replicationPeerStatistics"
                    )
            ))
            .orElseGet(() -> dataBackendReplicationPeerRepository.save(databaseReplicationPeerEntity));

        finalDatabaseDescriptionEntity.getReplicationPeerStatistics().add(
            DataBackendDatabaseReplicationPeerStatisticsEntityMapper.INSTANCE.toDataBackendDatabaseReplicationPeerStatisticsEntity(
                databaseDescriptionPeer,
                finalDatabaseDescriptionEntity,
                dataBackendStateExecution
            )
        );

        dataBackendReplicationPeerRepository.save(finalDatabaseDescriptionEntity);

        return finalDatabaseDescriptionEntity;
    }


    private DataBackendDatabaseSchemaEntity mapToDataBackendDatabaseSchemaEntity(DatabaseSchema databaseSchema,
                                                                                 DataBackendDatabaseEntity database,
                                                                                 DataBackendStateExecutionEntity dataBackendStateExecution) {

        final var databaseSchemaEntity = DataBackendDatabaseSchemaEntityMapper.INSTANCE.toDataBackendDatabaseSchemaEntity(
            databaseSchema,
            database,
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        );

        final var finalDatabaseSchemaEntity = dataBackendDatabaseSchemaRepository
            .findOne(Example.of(
                    databaseSchemaEntity,
                    ExampleMatcher.matching()
                        .withIgnorePaths(
                            "tables",
                            "views",
                            "indexes"
                        )
                )
            )
            .orElseGet(() -> dataBackendDatabaseSchemaRepository.save(databaseSchemaEntity));

        finalDatabaseSchemaEntity.setTables(
            StreamSupport.stream(databaseSchema.getTables().spliterator(), false)
                .map(databaseTable -> mapToSchemaTableEntity(
                    databaseTable,
                    finalDatabaseSchemaEntity,
                    dataBackendStateExecution
                ))
                .collect(Collectors.toList())
        );
        finalDatabaseSchemaEntity.setViews(
            StreamSupport.stream(databaseSchema.getViews().spliterator(), false)
                .map(databaseView -> mapToSchemaViewEntity(
                    databaseView,
                    finalDatabaseSchemaEntity,
                    dataBackendStateExecution
                ))
                .collect(Collectors.toList())
        );
        finalDatabaseSchemaEntity.setIndexes(
            StreamSupport.stream(databaseSchema.getIndexes().spliterator(), false)
                .map(databaseIndex -> mapToSchemaIndexEntity(
                    databaseIndex,
                    finalDatabaseSchemaEntity,
                    dataBackendStateExecution
                ))
                .collect(Collectors.toList())
        );

        return finalDatabaseSchemaEntity;
    }

    @SuppressWarnings("java:S1192")
    private DataBackendDatabaseSchemaTableEntity mapToSchemaTableEntity(DatabaseTable databaseTable,
                                                                        DataBackendDatabaseSchemaEntity schema,
                                                                        DataBackendStateExecutionEntity dataBackendStateExecution) {

        final var entity = DataBackendDatabaseSchemaTableEntityMapper.INSTANCE.toDataBackendDatabaseTableEntity(
            databaseTable,
            schema,
            new ArrayList<>()
        );

        final var finalEntity = dataBackendDatabaseSchemaTableRepository
            .findOne(Example.of(
                entity,
                ExampleMatcher.matching()
                    .withIgnorePaths(
                        "statistics"
                    )
            ))
            .orElseGet(() -> dataBackendDatabaseSchemaTableRepository.save(entity));

        finalEntity.getStatistics().add(
            DataBackendDatabaseSchemaTableStatisticsEntityMapper.INSTANCE.toDataBackendDatabaseTableStatisticsEntity(
                databaseTable,
                finalEntity,
                dataBackendStateExecution
            )
        );

        return finalEntity;
    }

    @SuppressWarnings("java:S1192")
    private DataBackendDatabaseSchemaViewEntity mapToSchemaViewEntity(DatabaseView databaseView,
                                                                      DataBackendDatabaseSchemaEntity schema,
                                                                      DataBackendStateExecutionEntity dataBackendStateExecution) {

        final var entity = DataBackendDatabaseSchemaViewEntityMapper.INSTANCE.toDataBackendDatabaseViewEntity(
            databaseView,
            schema,
            new ArrayList<>()
        );

        final var finalEntity = dataBackendDatabaseSchemaViewRepository
            .findOne(Example.of(
                entity,
                ExampleMatcher.matching()
                    .withIgnorePaths(
                        "statistics"
                    )
            ))
            .orElseGet(() -> dataBackendDatabaseSchemaViewRepository.save(entity));

        finalEntity.getStatistics().add(
            DataBackendDatabaseSchemaViewEntityStatisticsMapper.INSTANCE.toDataBackendDatabaseViewStatisticsEntity(
                databaseView,
                finalEntity,
                dataBackendStateExecution
            )
        );

        return finalEntity;
    }

    @SuppressWarnings("java:S1192")
    private DataBackendDatabaseSchemaIndexEntity mapToSchemaIndexEntity(DatabaseIndex databaseIndex,
                                                                        DataBackendDatabaseSchemaEntity schema,
                                                                        DataBackendStateExecutionEntity dataBackendStateExecution) {

        final var entity = DataBackendDatabaseSchemaIndexEntityMapper.INSTANCE.toDataBackendDatabaseIndexEntity(
            databaseIndex,
            schema,
            new ArrayList<>()
        );

        final var finalEntity = dataBackendDatabaseSchemaIndexRepository
            .findOne(Example.of(
                entity,
                ExampleMatcher.matching()
                    .withIgnorePaths(
                        "statistics"
                    )
            ))
            .orElseGet(() -> dataBackendDatabaseSchemaIndexRepository.save(entity));

        finalEntity.getStatistics().add(
            DataBackendDatabaseSchemaIndexStatisticsEntityMapper.INSTANCE.toDataBackendDatabaseIndexStatisticsEntity(
                databaseIndex,
                finalEntity,
                dataBackendStateExecution
            )
        );

        return finalEntity;
    }
}
