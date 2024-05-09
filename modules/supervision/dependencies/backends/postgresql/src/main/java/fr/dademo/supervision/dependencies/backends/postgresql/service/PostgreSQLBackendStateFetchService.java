/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.service;

import fr.dademo.supervision.dependencies.backends.model.DataBackendStateFetchService;
import fr.dademo.supervision.dependencies.backends.model.database.DatabaseDescription;
import fr.dademo.supervision.dependencies.backends.model.database.DatabaseDescriptionDefaultImpl;
import fr.dademo.supervision.dependencies.backends.model.database.GlobalDatabaseDescriptionDefaultImpl;
import fr.dademo.supervision.dependencies.backends.model.database.resources.*;
import fr.dademo.supervision.dependencies.backends.model.shared.*;
import fr.dademo.supervision.dependencies.backends.postgresql.configuration.ModuleBeans;
import fr.dademo.supervision.dependencies.backends.postgresql.configuration.ModuleConfiguration;
import fr.dademo.supervision.dependencies.backends.postgresql.repository.*;
import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseIndexEntity;
import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseTableEntity;
import fr.dademo.supervision.dependencies.backends.postgresql.service.mappers.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author dademo
 */
@Slf4j
@SuppressWarnings("unused")
@Service
public class PostgreSQLBackendStateFetchService implements DataBackendStateFetchService {

    public static final String MODULE_NAME = "";
    public static final String MODULE_VERSION = PostgreSQLBackendStateFetchService.class.getPackage().getImplementationVersion();
    private static final String ROLE_PRIMARY = "primary";
    private static final String ROLE_REPLICATION = "replication";

    @Autowired
    private ModuleConfiguration moduleConfiguration;

    @Qualifier(ModuleBeans.MODULE_DATA_SOURCE_BEAN_NAME)
    @Autowired
    private DataSource dataSource;

    @Autowired
    private DatabaseProductQueryRepository databaseProductQueryRepository;

    @Autowired
    private DatabaseReplicationPeerQueryRepository databaseReplicationPeerQueryRepository;

    @Autowired
    private DatabaseTablesAndViewsStatisticsQueryRepository databaseTablesAndViewsStatisticsQueryRepository;

    @Autowired
    private DatabaseIndexesStatisticsQueryRepository databaseIndexesStatisticsQueryRepository;

    @Autowired
    private DatabaseTableRowsCountQueryRepository databaseTableRowsCountQueryRepository;

    @Autowired
    private DatabaseGlobalStatisticsQueryRepository databaseGlobalStatisticsQueryRepository;

    @Autowired
    private DatabasesStatisticsQueryRepository databasesStatisticsQueryRepository;

    @Autowired
    private DatabasesConnectionsQueryRepository databasesConnectionsQueryRepository;

    @Nonnull
    @Override
    public DataBackendModuleMetaData getModuleMetaData() {

        return DataBackendModuleMetaDataDefaultImpl.builder()
            .moduleName(PostgreSQLBackendStateFetchService.class.getPackage().getName())
            .moduleTitle(PostgreSQLBackendStateFetchService.class.getPackage().getImplementationTitle())
            .moduleVersion(PostgreSQLBackendStateFetchService.class.getPackage().getImplementationVersion())
            .moduleVendor(PostgreSQLBackendStateFetchService.class.getPackage().getImplementationVendor())
            .build();
    }

    @Nonnull
    @Override
    public DataBackendDescription getDataBackendDescription() {

        final var objectBuilder = GlobalDatabaseDescriptionDefaultImpl.builder()
            .backendState(DataBackendState.READY)
            .backendStateExplanation(null)
            .backendName(getConnectionUrl())
            .primaryUrl(moduleConfiguration.getDatasource().getUrl())
            .startTime(databaseGlobalStatisticsQueryRepository.getDatabaseStartTime())
            .databaseConnections(getAllDatabasesConnections())
            .databasesDescriptions(getAllDatabasesDescriptions(false));
        applyDatabaseVersion(objectBuilder);
        applyReplicationAndNodes(objectBuilder);

        return objectBuilder.build();
    }

    @Nonnull
    @Override
    public DataBackendDescription getDataBackendDescriptionFull() {

        final var objectBuilder = GlobalDatabaseDescriptionDefaultImpl.builder()
            .backendState(DataBackendState.READY)
            .backendStateExplanation(null)
            .backendName(getConnectionUrl())
            .primaryUrl(moduleConfiguration.getDatasource().getUrl())
            .startTime(databaseGlobalStatisticsQueryRepository.getDatabaseStartTime())
            .databaseConnections(getAllDatabasesConnections())
            .databasesDescriptions(getAllDatabasesDescriptions(true));
        applyDatabaseVersion(objectBuilder);
        applyReplicationAndNodes(objectBuilder);

        return objectBuilder.build();
    }

    @Nullable
    private String getConnectionUrl() {

        try (var connection = dataSource.getConnection()) {
            return connection.getMetaData().getURL();
        } catch (SQLException e) {
            return null;
        }
    }

    private List<DatabaseDescription> getAllDatabasesDescriptions(boolean getRowsCount) {

        final var databasesStatistics = databasesStatisticsQueryRepository.getDatabasesStatistics();
        return databasesStatistics
            .stream()
            .map(new DatabaseStatisticsToBuilderValueMapper())
            .map(v -> v.databaseSchemas(new ArrayList<>(getSchemas(getRowsCount))))
            .map(DatabaseDescriptionDefaultImpl.DatabaseDescriptionDefaultImplBuilder::build)
            .toList();
    }

    private List<DatabaseConnection> getAllDatabasesConnections() {

        return databasesConnectionsQueryRepository.getDatabasesConnections()
            .stream()
            .map(new DatabaseConnectionValueMapper())
            .toList();
    }


    private void applyDatabaseVersion(GlobalDatabaseDescriptionDefaultImpl.GlobalDatabaseDescriptionDefaultImplBuilder<?, ?> objectBuilder) {

        final var databaseProductVersion = databaseProductQueryRepository.getDatabaseProductVersion();
        objectBuilder
            .backendProductName(databaseProductVersion.getProductNameFull())
            .backendProductVersion(databaseProductVersion.getProductVersion());
    }

    private void applyReplicationAndNodes(GlobalDatabaseDescriptionDefaultImpl.GlobalDatabaseDescriptionDefaultImplBuilder<?, ?> objectBuilder) {

        final var replicationHosts = Stream.concat(
                databaseReplicationPeerQueryRepository.getDatabaseControllerReplicationInformations().stream(),
                databaseReplicationPeerQueryRepository.getDatabaseClientReplicationInformations().stream()
            )
            .map(new DatabaseReplicationPeerValueMapper())
            .toList();

        objectBuilder
            .backendNodes(
                Stream.concat(
                    Stream.of(moduleConfiguration.getDatasource().getUrl())
                        .map(backendNodeBuilder(true)),
                    replicationHosts.stream()
                        .map(DatabaseReplicationPeer::getPeerHostName)
                        .map(backendNodeBuilder(false))
                ).toList())
            .clusterSize(replicationHosts.size() + 1)
            .primaryCount(1)
            .replicaCount(replicationHosts.size())
            .databaseReplicationPeers(replicationHosts);
    }

    private List<? extends DatabaseSchema> getSchemas(boolean getRowsCount) {

        final var databaseTableEntities = databaseTablesAndViewsStatisticsQueryRepository.getDatabaseTablesAndViewsStatistics();
        final var databaseIndexEntities = databaseIndexesStatisticsQueryRepository.getDatabaseIndexesStatistics();

        final var databaseSchemas = databaseTableEntities
            .stream()
            .map(DatabaseTableEntity::getSchema)
            .sorted()
            .distinct()
            .map(new DatabaseSchemaValueMapper())
            .toList();

        databaseSchemas.forEach(mergeWithDatabaseTablesAndIndexes(databaseTableEntities, databaseIndexEntities, getRowsCount));

        return databaseSchemas;
    }

    private Consumer<DatabaseSchemaDefaultImpl> mergeWithDatabaseTablesAndIndexes(List<DatabaseTableEntity> databaseTableEntities,
                                                                                  List<DatabaseIndexEntity> databaseIndexEntities,
                                                                                  boolean getRowsCount) {

        return databaseSchema -> {

            final var tables = databaseTableEntities
                .stream()
                .filter(e -> e.getSchema().equals(databaseSchema.getName()))
                .filter(e -> e.getType().toUpperCase().contains("TABLE"))
                .map(new DatabaseTableValueMapper())
                .toList();

            final var views = databaseTableEntities
                .stream()
                .filter(e -> e.getSchema().equals(databaseSchema.getName()))
                .filter(e -> e.getType().toUpperCase().contains("VIEW"))
                .map(new DatabaseViewValueMapper())
                .toList();

            final var indexes = databaseIndexEntities
                .stream()
                .filter(e -> e.getSchema().equals(databaseSchema.getName()))
                .map(new DatabaseIndexValueMapper())
                .toList();

            if (getRowsCount) {
                tables.forEach(tableRowsCountSetterForSchema(databaseSchema));
                views.forEach(viewRowsCountSetterForSchema(databaseSchema));
            }

            databaseSchema.setTables(new ArrayList<>(tables));
            databaseSchema.setViews(new ArrayList<>(views));
            databaseSchema.setIndexes(new ArrayList<>(indexes));
        };
    }

    private Consumer<DatabaseTableDefaultImpl> tableRowsCountSetterForSchema(DatabaseSchemaDefaultImpl databaseSchema) {

        return databaseTable -> databaseTable.setRowsCount(
            databaseTableRowsCountQueryRepository
                .getRowCountForTable(databaseSchema.getName(), databaseTable.getName())
                .getRowCount()
        );
    }

    private Consumer<DatabaseViewDefaultImpl> viewRowsCountSetterForSchema(DatabaseSchemaDefaultImpl databaseSchema) {

        return databaseTable -> databaseTable.setRowsCount(
            databaseTableRowsCountQueryRepository
                .getRowCountForTable(databaseSchema.getName(), databaseTable.getName())
                .getRowCount()
        );
    }

    private Function<String, DataBackendClusterNode> backendNodeBuilder(boolean isPrimary) {

        return url -> DataBackendClusterNodeDefaultImpl.builder()
            .url(url)
            .role(isPrimary ? ROLE_PRIMARY : ROLE_REPLICATION)
            .build();
    }
}
