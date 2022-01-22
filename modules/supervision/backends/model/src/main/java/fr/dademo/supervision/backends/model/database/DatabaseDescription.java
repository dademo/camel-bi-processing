/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.model.database;

import fr.dademo.supervision.backends.model.database.resources.DatabaseSchema;
import fr.dademo.supervision.backends.model.shared.DataBackendDescriptionBase;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.Date;

/**
 * @author dademo
 */
public interface DatabaseDescription extends DataBackendDescriptionBase {

    @Nullable
    @Size(min = 1, max = 255)
    String getName();

    @Nullable
    @Min(0)
    Long getCommitCounts();

    @Nullable
    @Min(0)
    Long getRollbacksCounts();

    @Nullable
    @Min(0)
    Long getBufferBlocksRead();

    @Nullable
    @Min(0)
    Long getDiskBlocksRead();

    @Nullable
    @Min(0)
    Long getReturnedRowsCount();

    @Nullable
    @Min(0)
    Long getFetchedRowsCount();

    @Nullable
    @Min(0)
    Long getInsertedRowsCount();

    @Nullable
    @Min(0)
    Long getUpdatedRowsCount();

    @Nullable
    @Min(0)
    Long getDeletedRowsCount();

    @Nullable
    @Min(0)
    Long getConflictsCount();

    @Nullable
    @Min(0)
    Long getDeadlocksCount();

    @Nullable
    Duration getReadTime();

    @Nullable
    Duration getWriteTime();

    @Nullable
    Date getLastStatisticsResetTime();

    @NonNull
    Iterable<DatabaseSchema> getDatabaseSchemas();
}
