/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services;

import fr.dademo.supervision.backends.model.database.DatabaseDescription;
import fr.dademo.supervision.backends.model.database.GlobalDatabaseDescription;
import fr.dademo.supervision.backends.model.database.resources.DatabaseSchema;
import fr.dademo.supervision.backends.model.shared.DataBackendModuleMetaData;
import fr.dademo.supervision.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.entities.database.database.DataBackendDatabaseEntity;
import fr.dademo.supervision.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
import fr.dademo.supervision.entities.database.globaldatabase.DataBackendGlobalDatabaseEntity;
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
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author dademo
 */
@Service
public class DatabaseBackendMappingServiceImplGeneric extends AbstractGenericDataBackendMappingService implements DatabaseBackendMappingService {

    @SuppressWarnings("ConstantConditions")
    @Nonnull
    @Override
    public DataBackendStateExecutionEntity mapModuleDataToEntity(
        @Nonnull DataBackendModuleMetaData backendModuleMetaData,
        @Nonnull GlobalDatabaseDescription globalDatabaseDescription) {

        final var backendStateExecutionEntity = mapCommonModuleDataToEntity(
            backendModuleMetaData,
            globalDatabaseDescription
        ).build();

        backendStateExecutionEntity.setGlobalDatabase(mapToGlobalDatabaseEntity(globalDatabaseDescription, backendStateExecutionEntity));

        // Reverse links
        backendStateExecutionEntity.getGlobalDatabase().setBackendStateExecution(backendStateExecutionEntity);
        backendStateExecutionEntity.getDataBackendModuleMetaData().setBackendStateExecutions(Collections.singletonList(backendStateExecutionEntity));
        backendStateExecutionEntity.getDataBackendDescription().setBackendStateExecutions(Collections.singletonList(backendStateExecutionEntity));


        return backendStateExecutionEntity;
    }

    private DataBackendGlobalDatabaseEntity mapToGlobalDatabaseEntity(GlobalDatabaseDescription globalDatabaseDescription,
                                                                      DataBackendStateExecutionEntity dataBackendStateExecution) {

        final var globalDatabaseDescriptionEntity = DataBackendGlobalDatabaseEntity.builder()
            .connections(
                StreamSupport.stream(globalDatabaseDescription.getDatabaseConnections().spliterator(), false)
                    .map(databaseConnection -> DataBackendDatabaseConnectionEntityMapper.INSTANCE.toDataBackendGlobalDatabaseDescriptionEntity(
                        databaseConnection,
                        dataBackendStateExecution
                    ))
                    .collect(Collectors.toList())
            )
            .databases(
                StreamSupport.stream(globalDatabaseDescription.getDatabasesDescriptions().spliterator(), false)
                    .map(databaseDescription -> mapToDataBackendDatabaseDescriptionEntity(
                        databaseDescription,
                        dataBackendStateExecution
                    ))
                    .collect(Collectors.toList())
            )
            .build();

        // Reverse links
        globalDatabaseDescriptionEntity.getConnections().forEach(connection -> connection.setGlobalDatabase(globalDatabaseDescriptionEntity));
        globalDatabaseDescriptionEntity.getDatabases().forEach(database -> database.setGlobalDatabase(globalDatabaseDescriptionEntity));

        return globalDatabaseDescriptionEntity;
    }

    private DataBackendDatabaseEntity mapToDataBackendDatabaseDescriptionEntity(DatabaseDescription databaseDescription,
                                                                                DataBackendStateExecutionEntity dataBackendStateExecution) {

        final var databaseDescriptionEntity = DataBackendDatabaseEntityMapper.INSTANCE.toDataBackendDatabaseEntity(
            databaseDescription,
            StreamSupport.stream(databaseDescription.getDatabaseSchemas().spliterator(), false)
                .map(databaseSchema -> mapToDataBackendDatabaseSchemaEntity(databaseSchema, dataBackendStateExecution))
                .collect(Collectors.toList()),
            Collections.singletonList(
                DataBackendDatabaseStatisticsEntityMapper.INSTANCE.toDataBackendDatabaseStatisticsEntity(
                    databaseDescription,
                    dataBackendStateExecution
                )
            )
        );

        // Reverse links
        databaseDescriptionEntity.getSchemas().forEach(schema -> schema.setDatabase(databaseDescriptionEntity));
        databaseDescriptionEntity.getDatabaseStatistics().forEach(databaseStatistics -> databaseStatistics.setDatabase(databaseDescriptionEntity));

        return databaseDescriptionEntity;
    }

    private DataBackendDatabaseSchemaEntity mapToDataBackendDatabaseSchemaEntity(DatabaseSchema databaseSchema,
                                                                                 DataBackendStateExecutionEntity dataBackendStateExecution) {

        final var databaseSchemaEntity = DataBackendDatabaseSchemaEntityMapper.INSTANCE.toDataBackendDatabaseSchemaEntity(
            databaseSchema,
            StreamSupport.stream(databaseSchema.getTables().spliterator(), false)
                .map(databaseTable -> DataBackendDatabaseSchemaTableEntityMapper.INSTANCE.toDataBackendDatabaseTableEntity(
                    databaseTable,
                    Collections.singletonList(
                        DataBackendDatabaseSchemaTableStatisticsEntityMapper.INSTANCE.toDataBackendDatabaseTableStatisticsEntity(
                            databaseTable,
                            dataBackendStateExecution
                        )
                    )
                ))
                .collect(Collectors.toList()),
            StreamSupport.stream(databaseSchema.getViews().spliterator(), false)
                .map(databaseView -> DataBackendDatabaseSchemaViewEntityMapper.INSTANCE.toDataBackendDatabaseViewEntity(
                    databaseView,
                    Collections.singletonList(
                        DataBackendDatabaseSchemaViewEntityStatisticsMapper.INSTANCE.toDataBackendDatabaseViewStatisticsEntity(
                            databaseView,
                            dataBackendStateExecution
                        )
                    )
                ))
                .collect(Collectors.toList()),
            StreamSupport.stream(databaseSchema.getIndexes().spliterator(), false)
                .map(databaseIndex -> DataBackendDatabaseSchemaIndexEntityMapper.INSTANCE.toDataBackendDatabaseIndexEntity(
                    databaseIndex,
                    Collections.singletonList(
                        DataBackendDatabaseSchemaIndexStatisticsEntityMapper.INSTANCE.toDataBackendDatabaseIndexStatisticsEntity(
                            databaseIndex,
                            dataBackendStateExecution
                        )
                    )
                ))
                .collect(Collectors.toList())
        );

        // Reverse links
        databaseSchemaEntity.getTables().forEach(table -> {
            table.setSchema(databaseSchemaEntity);
            table.getStatistics().forEach(statistic -> statistic.setTable(table));
        });
        databaseSchemaEntity.getViews().forEach(view -> {
            view.setSchema(databaseSchemaEntity);
            view.getStatistics().forEach(statistic -> statistic.setView(view));
        });
        databaseSchemaEntity.getIndexes().forEach(index -> {
            index.setSchema(databaseSchemaEntity);
            index.getStatistics().forEach(statistic -> statistic.setIndex(index));
        });

        return databaseSchemaEntity;
    }
}
