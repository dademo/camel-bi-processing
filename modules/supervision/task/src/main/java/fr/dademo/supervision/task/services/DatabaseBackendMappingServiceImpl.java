/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services;

import fr.dademo.supervision.backends.model.database.DatabaseDescription;
import fr.dademo.supervision.backends.model.database.GlobalDatabaseDescription;
import fr.dademo.supervision.backends.model.database.resources.DatabaseIndex;
import fr.dademo.supervision.backends.model.database.resources.DatabaseSchema;
import fr.dademo.supervision.backends.model.database.resources.DatabaseTable;
import fr.dademo.supervision.backends.model.database.resources.DatabaseView;
import fr.dademo.supervision.backends.model.shared.DataBackendModuleMetaData;
import fr.dademo.supervision.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.entities.database.database.DataBackendDatabaseEntity;
import fr.dademo.supervision.entities.database.databaseindex.DataBackendDatabaseSchemaIndexEntity;
import fr.dademo.supervision.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
import fr.dademo.supervision.entities.database.databasetable.DataBackendDatabaseSchemaTableEntity;
import fr.dademo.supervision.entities.database.databaseview.DataBackendDatabaseSchemaViewEntity;
import fr.dademo.supervision.entities.database.globaldatabase.DataBackendGlobalDatabaseEntity;
import fr.dademo.supervision.task.repositories.database.*;
import fr.dademo.supervision.task.services.mappers.database.connection.DataBackendDatabaseConnectionEntityMapper;
import fr.dademo.supervision.task.services.mappers.database.database.DataBackendDatabaseEntityMapper;
import fr.dademo.supervision.task.services.mappers.database.database.DataBackendDatabaseStatisticsEntityMapper;
import fr.dademo.supervision.task.services.mappers.database.databaseindex.DataBackendDatabaseSchemaIndexEntityMapper;
import fr.dademo.supervision.task.services.mappers.database.databaseindex.DataBackendDatabaseSchemaIndexStatisticsEntityMapper;
import fr.dademo.supervision.task.services.mappers.database.databaseschema.DataBackendDatabaseSchemaEntityMapper;
import fr.dademo.supervision.task.services.mappers.database.databasetable.DataBackendDatabaseSchemaTableEntityMapper;
import fr.dademo.supervision.task.services.mappers.database.databasetable.DataBackendDatabaseSchemaTableStatisticsEntityMapper;
import fr.dademo.supervision.task.services.mappers.database.databaseview.DataBackendDatabaseSchemaViewEntityMapper;
import fr.dademo.supervision.task.services.mappers.database.databaseview.DataBackendDatabaseSchemaViewEntityStatisticsMapper;
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
    private DataBackendGlobalDatabaseRepository dataBackendGlobalDatabaseRepository;

    @Autowired
    private DataBackendDatabaseRepository dataBackendDatabaseRepository;

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

        backendStateExecutionEntity.setGlobalDatabase(mapToGlobalDatabaseEntity(globalDatabaseDescription, backendStateExecutionEntity));

        // Reverse links
        backendStateExecutionEntity.getDataBackendModuleMetaData().getBackendStateExecutions().add(backendStateExecutionEntity);
        backendStateExecutionEntity.getDataBackendDescription().getBackendStateExecutions().add(backendStateExecutionEntity);

        return backendStateExecutionEntity;
    }

    private DataBackendGlobalDatabaseEntity mapToGlobalDatabaseEntity(GlobalDatabaseDescription globalDatabaseDescription,
                                                                      DataBackendStateExecutionEntity dataBackendStateExecution) {

        final var globalDatabaseDescriptionEntity = dataBackendGlobalDatabaseRepository.save(
            DataBackendGlobalDatabaseEntity.builder()
                .connections(new ArrayList<>())
                .databases(new ArrayList<>())
                .backendStateExecution(dataBackendStateExecution)
                .build()
        );

        globalDatabaseDescriptionEntity.setConnections(
            StreamSupport.stream(globalDatabaseDescription.getDatabaseConnections().spliterator(), false)
                .map(databaseConnection -> DataBackendDatabaseConnectionEntityMapper.INSTANCE.toDataBackendGlobalDatabaseDescriptionEntity(
                    databaseConnection,
                    globalDatabaseDescriptionEntity,
                    dataBackendStateExecution
                ))
                .collect(Collectors.toList())
        );
        globalDatabaseDescriptionEntity.setDatabases(
            StreamSupport.stream(globalDatabaseDescription.getDatabasesDescriptions().spliterator(), false)
                .map(databaseDescription -> mapToDataBackendDatabaseDescriptionEntity(
                    databaseDescription,
                    globalDatabaseDescriptionEntity,
                    dataBackendStateExecution
                ))
                .collect(Collectors.toList())
        );

        // Reverse links
        globalDatabaseDescriptionEntity.getConnections().forEach(connection -> connection.setGlobalDatabase(globalDatabaseDescriptionEntity));
        globalDatabaseDescriptionEntity.getDatabases().forEach(database -> database.setGlobalDatabase(globalDatabaseDescriptionEntity));

        return globalDatabaseDescriptionEntity;
    }

    private DataBackendDatabaseEntity mapToDataBackendDatabaseDescriptionEntity(DatabaseDescription databaseDescription,
                                                                                DataBackendGlobalDatabaseEntity globalDatabase,
                                                                                DataBackendStateExecutionEntity dataBackendStateExecution) {

        final var databaseDescriptionEntity = DataBackendDatabaseEntityMapper.INSTANCE.toDataBackendDatabaseEntity(
            databaseDescription,
            globalDatabase,
            new ArrayList<>(),
            new ArrayList<>()
        );

        final var finalDatabaseDescriptionEntity = dataBackendDatabaseRepository
            .findOne(Example.of(databaseDescriptionEntity,
                ExampleMatcher.matching()
                    .withIgnorePaths(
                        "databaseStatistics",
                        "schemas"
                    )
            ))
            .orElseGet(() -> dataBackendDatabaseRepository.save(databaseDescriptionEntity));

        finalDatabaseDescriptionEntity.getDatabaseStatistics().add(
            DataBackendDatabaseStatisticsEntityMapper.INSTANCE.toDataBackendDatabaseStatisticsEntity(
                databaseDescription,
                finalDatabaseDescriptionEntity,
                dataBackendStateExecution
            )
        );

        finalDatabaseDescriptionEntity.setSchemas(
            StreamSupport.stream(databaseDescription.getDatabaseSchemas().spliterator(), false)
                .map(databaseSchema -> mapToDataBackendDatabaseSchemaEntity(databaseSchema, finalDatabaseDescriptionEntity, dataBackendStateExecution))
                .collect(Collectors.toList())
        );

        // Reverse links
        finalDatabaseDescriptionEntity.getDatabaseStatistics().forEach(databaseStatistics -> databaseStatistics.setDatabase(finalDatabaseDescriptionEntity));

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

        // Reverse links
        finalDatabaseSchemaEntity.getTables().forEach(table -> {
            table.setSchema(finalDatabaseSchemaEntity);
            table.getStatistics().forEach(statistic -> statistic.setTable(table));
        });
        finalDatabaseSchemaEntity.getViews().forEach(view -> {
            view.setSchema(finalDatabaseSchemaEntity);
            view.getStatistics().forEach(statistic -> statistic.setView(view));
        });
        finalDatabaseSchemaEntity.getIndexes().forEach(index -> {
            index.setSchema(finalDatabaseSchemaEntity);
            index.getStatistics().forEach(statistic -> statistic.setIndex(index));
        });

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
