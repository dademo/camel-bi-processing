/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services;

import fr.dademo.supervision.backends.model.shared.DataBackendDescription;
import fr.dademo.supervision.backends.model.shared.DataBackendModuleMetaData;
import fr.dademo.supervision.task.repositories.DatabaseBackendStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@Service
public class DataBackendPersistenceServiceImpl implements DataBackendPersistenceService {

    @Autowired
    private DataBackendMappingService dataBackendMappingService;

    @Autowired
    private DatabaseBackendStateRepository databaseBackendStateRepository;

    @Override
    public void persistBackendFetchResult(@Nonnull DataBackendModuleMetaData backendModuleMetaData,
                                          @Nonnull DataBackendDescription dataBackendDescription) {
        switch (dataBackendDescription.getBackendKind()) {
            case DATABASE:
                persistDatabaseBackendFetchResult(backendModuleMetaData, dataBackendDescription);
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
        @Nonnull DataBackendDescription dataBackendDescription) {
        databaseBackendStateRepository.saveAndFlush(
            dataBackendMappingService.mapModuleDataToEntity(
                backendModuleMetaData, dataBackendDescription
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
