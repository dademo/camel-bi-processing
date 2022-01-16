/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.service;

import fr.dademo.supervision.backends.model.DataBackendStateFetchService;
import fr.dademo.supervision.backends.model.database.DatabaseDescription;
import fr.dademo.supervision.backends.model.database.DatabaseDescriptionDefaultImpl;
import fr.dademo.supervision.backends.model.shared.DataBackendDescription;
import fr.dademo.supervision.backends.model.shared.DataBackendState;
import fr.dademo.supervision.backends.postgresql.configuration.ModuleConfiguration;
import fr.dademo.supervision.backends.postgresql.repository.DatabaseProductQueryRepository;
import fr.dademo.supervision.backends.postgresql.repository.DatabaseStatsQueryRepository;
import fr.dademo.supervision.backends.postgresql.repository.DatabaseTablesQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;
import java.net.URL;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.dademo.supervision.backends.postgresql.configuration.ModuleBeans.MODULE_DATASOURCE_BEAN_NAME;

/**
 * @author dademo
 */
public class PostgreSQLBackendStateFetchService implements DataBackendStateFetchService<DatabaseDescription> {

    @Autowired
    private ModuleConfiguration moduleConfiguration;

    @Qualifier(MODULE_DATASOURCE_BEAN_NAME)
    @Autowired
    private DataSource dataSource;

    @Autowired
    private DatabaseProductQueryRepository databaseProductQueryRepository;

    @Autowired
    private DatabaseStatsQueryRepository databaseStatsQueryRepository;

    @Autowired
    private DatabaseTablesQueryRepository databaseTablesQueryRepository;

    @Override
    public DataBackendDescription getDataBackendDescription() {

        final var objectBuilder = DatabaseDescriptionDefaultImpl.builder()
            .dataBackendState(DataBackendState.READY)
            .dataBackendStateExplanation(null)
            .backendName(moduleConfiguration.getDataSourceUrl().getHost())
            .primaryUrl(moduleConfiguration.getDataSourceUrl());
        applyDatabaseVersion(objectBuilder);
        applyReplication(objectBuilder);

        return objectBuilder.build();
        /*
            .sizeBytes()
            .effectiveSizeBytes()
            .availableSizeBytes()
            .build();
         */
    }

    private void applyDatabaseVersion(DatabaseDescriptionDefaultImpl.DatabaseDescriptionDefaultImplBuilder<?, ?> o) {

        final var databaseProductVersion = databaseProductQueryRepository.getDatabaseProductVersion();
        o
            .backendProductName(databaseProductVersion.getProductNameFull())
            .backendProductVersion(databaseProductVersion.getProductVersion());
    }

    private void applyReplication(DatabaseDescriptionDefaultImpl.DatabaseDescriptionDefaultImplBuilder<?, ?> o) {

        // TODO: replication hosts
        final var replicationHosts = Collections.<URL>emptyList();
        o
            .nodeUrls(Stream.concat(
                replicationHosts.stream(),
                Stream.of(moduleConfiguration.getDataSourceUrl())
            ).collect(Collectors.toList()))
            .clusterSize(replicationHosts.size() + 1)
            .primaryCount(1)
            .replicaCount(replicationHosts.size());
    }
}
