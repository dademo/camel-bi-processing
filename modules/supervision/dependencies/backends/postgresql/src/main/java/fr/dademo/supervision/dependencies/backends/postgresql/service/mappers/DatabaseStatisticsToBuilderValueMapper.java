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

package fr.dademo.supervision.dependencies.backends.postgresql.service.mappers;

import fr.dademo.supervision.dependencies.backends.model.database.DatabaseDescriptionDefaultImpl;
import fr.dademo.supervision.dependencies.backends.postgresql.repository.entities.DatabaseStatisticsEntity;

import java.util.function.Function;

/**
 * @author dademo
 */
public class DatabaseStatisticsToBuilderValueMapper implements Function<DatabaseStatisticsEntity, DatabaseDescriptionDefaultImpl.DatabaseDescriptionDefaultImplBuilder<?, ?>> {

    @Override
    public DatabaseDescriptionDefaultImpl.DatabaseDescriptionDefaultImplBuilder<?, ?> apply(DatabaseStatisticsEntity databaseStatisticsEntity) {

        return DatabaseDescriptionDefaultImpl.builder()
            .name(databaseStatisticsEntity.getName())
            .commitCounts(databaseStatisticsEntity.getCommitCounts())
            .rollbacksCounts(databaseStatisticsEntity.getRollbackCounts())
            .bufferBlocksRead(databaseStatisticsEntity.getBufferBlocksRead())
            .diskBlocksRead(databaseStatisticsEntity.getDiskBlocksRead())
            .returnedRowsCount(databaseStatisticsEntity.getReturnedRowsCount())
            .fetchedRowsCount(databaseStatisticsEntity.getFetchedRowsCount())
            .insertedRowsCount(databaseStatisticsEntity.getInsertedRowsCount())
            .updatedRowsCount(databaseStatisticsEntity.getUpdatedRowsCount())
            .deletedRowsCount(databaseStatisticsEntity.getDeletedRowsCount())
            .conflictsCount(databaseStatisticsEntity.getConflictsCount())
            .deadlocksCount(databaseStatisticsEntity.getDeadlocksCount())
            .readTime(databaseStatisticsEntity.getReadTime())
            .writeTime(databaseStatisticsEntity.getWriteTime())
            .lastStatisticsResetTime(databaseStatisticsEntity.getLastStatisticsResetTime());
    }
}
