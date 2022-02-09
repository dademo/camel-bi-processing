/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.postgresql.service.mappers;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseTableDefaultImpl;
import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseTableEntity;

import java.util.function.Function;

/**
 * @author dademo
 */
public class DatabaseTableValueMapper implements Function<DatabaseTableEntity, DatabaseTableDefaultImpl> {

    @Override
    public DatabaseTableDefaultImpl apply(DatabaseTableEntity databaseTableEntity) {

        return DatabaseTableDefaultImpl.builder()
            .name(databaseTableEntity.getName())
            .sequentialScansCount(databaseTableEntity.getSequentialScansCount())
            .sequentialScansFetchedRowsCount(databaseTableEntity.getSequentialScansFetchedRowsCount())
            .indexScansCount(databaseTableEntity.getIndexScansCount())
            .indexScansFetchedRowsCount(databaseTableEntity.getIndexScansFetchedRowsCount())
            .insertedRowsCount(databaseTableEntity.getInsertedRowsCount())
            .updatedRowsCount(databaseTableEntity.getUpdatedRowsCount())
            .deletedRowsCount(databaseTableEntity.getDeletedRowsCount())
            .hotUpdatedRowsCount(databaseTableEntity.getHotUpdatedRowsCount())
            .liveRowsCount(databaseTableEntity.getLiveRowsCount())
            .deadRowsCount(databaseTableEntity.getDeadRowsCount())
            .lastVacuum(databaseTableEntity.getLastVacuum())
            .lastAutoVacuum(databaseTableEntity.getLastAutoVacuum())
            .lastAnalyze(databaseTableEntity.getLastAnalyze())
            .lastAutoAnalyze(databaseTableEntity.getLastAutoAnalyze())
            .vacuumCount(databaseTableEntity.getVacuumCount())
            .autoVacuumCount(databaseTableEntity.getAutoVacuumCount())
            .analyzeCount(databaseTableEntity.getAnalyzeCount())
            .autoAnalyzeCount(databaseTableEntity.getAutoAnalyzeCount())
            .tableBlocksDiskRead(databaseTableEntity.getTableBlocksDiskRead())
            .tableBlocksCacheRead(databaseTableEntity.getTableBlocksCacheRead())
            .indexesDiskRead(databaseTableEntity.getIndexesDiskRead())
            .indexesCacheRead(databaseTableEntity.getIndexesCacheRead())
            .tableToastDiskRead(databaseTableEntity.getTableToastDiskRead())
            .tableToastCacheRead(databaseTableEntity.getTableToastCacheRead())
            .indexesToastDiskRead(databaseTableEntity.getIndexesToastDiskRead())
            .indexesToastCacheRead(databaseTableEntity.getIndexesToastCacheRead())
            .build();
    }
}
