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
import fr.dademo.supervision.entities.database.DataBackendDatabaseDescriptionEntity;
import fr.dademo.supervision.entities.database.DataBackendDatabaseSchemaEntity;
import fr.dademo.supervision.entities.database.DataBackendGlobalDatabaseDescriptionEntity;
import fr.dademo.supervision.task.services.mappers.database.*;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
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
        )
            .globalDatabase(mapToGlobalDatabaseEntity(globalDatabaseDescription))
            .build();

        // Reverse links
        backendStateExecutionEntity.getGlobalDatabase().setBackendStateExecution(backendStateExecutionEntity);


        return backendStateExecutionEntity;
    }

    private DataBackendGlobalDatabaseDescriptionEntity mapToGlobalDatabaseEntity(GlobalDatabaseDescription globalDatabaseDescription) {

        final var globalDatabaseDescriptionEntity = DataBackendGlobalDatabaseDescriptionEntity.builder()
            .connections(
                StreamSupport.stream(globalDatabaseDescription.getDatabaseConnections().spliterator(), false)
                    .map(DataBackendDatabaseConnectionEntityMapper.INSTANCE::toDataBackendGlobalDatabaseDescriptionEntity)
                    .collect(Collectors.toList())
            )
            .databases(
                StreamSupport.stream(globalDatabaseDescription.getDatabasesDescriptions().spliterator(), false)
                    .map(this::mapToDataBackendDatabaseDescriptionEntity)
                    .collect(Collectors.toList())
            )
            .build();

        // Reverse links
        globalDatabaseDescriptionEntity.getConnections().forEach(connection -> connection.setGlobalDatabase(globalDatabaseDescriptionEntity));
        globalDatabaseDescriptionEntity.getDatabases().forEach(database -> database.setGlobalDatabase(globalDatabaseDescriptionEntity));

        return globalDatabaseDescriptionEntity;
    }

    private DataBackendDatabaseDescriptionEntity mapToDataBackendDatabaseDescriptionEntity(DatabaseDescription databaseDescription) {

        final var databaseDescriptionEntity = DataBackendDatabaseDescriptionEntityMapper.INSTANCE.toDataBackendDatabaseDescriptionEntity(
            databaseDescription,
            StreamSupport.stream(databaseDescription.getDatabaseSchemas().spliterator(), false)
                .map(this::mapToDataBackendDatabaseSchemaEntity)
                .collect(Collectors.toList())
        );

        // Reverse links
        databaseDescriptionEntity.getSchemas().forEach(schema -> schema.setDatabase(databaseDescriptionEntity));

        return databaseDescriptionEntity;
    }

    private DataBackendDatabaseSchemaEntity mapToDataBackendDatabaseSchemaEntity(DatabaseSchema databaseSchema) {

        final var databaseSchemaEntity = DataBackendDatabaseSchemaEntityMapper.INSTANCE.toDataBackendDatabaseSchemaEntity(
            databaseSchema,
            StreamSupport.stream(databaseSchema.getTables().spliterator(), false)
                .map(DataBackendDatabaseTableEntityMapper.INSTANCE::toDataBackendDatabaseTableEntity)
                .collect(Collectors.toList()),
            StreamSupport.stream(databaseSchema.getViews().spliterator(), false)
                .map(DataBackendDatabaseViewEntityMapper.INSTANCE::toDataBackendDatabaseViewEntity)
                .collect(Collectors.toList()),
            StreamSupport.stream(databaseSchema.getIndexes().spliterator(), false)
                .map(DataBackendDatabaseIndexEntityMapper.INSTANCE::toDataBackendDatabaseIndexEntity)
                .collect(Collectors.toList())
        );

        // Reverse links
        databaseSchemaEntity.getTables().forEach(table -> table.setSchema(databaseSchemaEntity));
        databaseSchemaEntity.getViews().forEach(view -> view.setSchema(databaseSchemaEntity));
        databaseSchemaEntity.getIndexes().forEach(index -> index.setSchema(databaseSchemaEntity));

        return databaseSchemaEntity;
    }
}
