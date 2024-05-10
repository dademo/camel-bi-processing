/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services;

import fr.dademo.supervision.dependencies.backends.model.database.GlobalDatabaseDescription;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescription;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendModuleMetaData;
import fr.dademo.supervision.dependencies.persistence.PersistenceBeans;
import fr.dademo.supervision.dependencies.persistence.services.exceptions.InvalidDataBackendDescriptionType;
import fr.dademo.supervision.dependencies.repositories.DatabaseBackendStateRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dademo
 */
@Service
public class DataBackendPersistenceServiceImpl implements DataBackendPersistenceService {

    @Autowired
    private DatabaseBackendMappingService databaseBackendMappingService;

    @Autowired
    private DatabaseBackendStateRepository databaseBackendStateRepository;

    @Override
    @Transactional(PersistenceBeans.PERSISTENCE_TRANSACTION_MANAGER_BEAN_NAME)
    public void persistBackendFetchResult(@Nonnull DataBackendModuleMetaData backendModuleMetaData,
                                          @Nonnull DataBackendDescription dataBackendDescription) {
        switch (dataBackendDescription.getBackendKind()) {
            case DATABASE:
                // Guard to avoid bad casting
                if (!GlobalDatabaseDescription.class.isAssignableFrom(dataBackendDescription.getClass())) {
                    throw new InvalidDataBackendDescriptionType(GlobalDatabaseDescription.class, dataBackendDescription);
                }
                persistDatabaseBackendFetchResult(backendModuleMetaData, (GlobalDatabaseDescription) dataBackendDescription);
                break;
            case FILE_SYSTEM:
                persistFileSystemBackendFetchResult(backendModuleMetaData, dataBackendDescription);
                break;
            case MESSAGE_QUEUE:
                persistMessageQueueBackendFetchResult(backendModuleMetaData, dataBackendDescription);
                break;
            case OTHER:
            default:
                persistOtherBackendFetchResult(backendModuleMetaData, dataBackendDescription);
                break;
        }
    }

    private void persistDatabaseBackendFetchResult(
        @Nonnull DataBackendModuleMetaData backendModuleMetaData,
        @Nonnull GlobalDatabaseDescription globalDatabaseDescription) {

        databaseBackendStateRepository.saveAndFlush(
            databaseBackendMappingService.mapModuleDataToDatabaseEntity(
                backendModuleMetaData, globalDatabaseDescription
            ));
    }

    @SuppressWarnings("unused")
    private void persistFileSystemBackendFetchResult(
        @Nonnull DataBackendModuleMetaData backendModuleMetaData,
        @Nonnull DataBackendDescription dataBackendDescription) {
        // TODO To be implemented
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unused")
    private void persistMessageQueueBackendFetchResult(
        @Nonnull DataBackendModuleMetaData backendModuleMetaData,
        @Nonnull DataBackendDescription dataBackendDescription) {
        // TODO To be implemented
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unused")
    private void persistOtherBackendFetchResult(
        @Nonnull DataBackendModuleMetaData backendModuleMetaData,
        @Nonnull DataBackendDescription dataBackendDescription) {
        // TODO To be implemented
        throw new UnsupportedOperationException();
    }
}
