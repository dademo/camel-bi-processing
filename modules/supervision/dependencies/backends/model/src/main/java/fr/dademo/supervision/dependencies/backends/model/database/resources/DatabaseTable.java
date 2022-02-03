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

package fr.dademo.supervision.dependencies.backends.model.database.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DatabaseTable {

    @Nonnull
    @Size(min = 1, max = 255)
    String getName();

    @Nullable
    @Min(0)
    Long getRowsCount();

    @Nullable
    @Min(0)
    Long getTotalSize();

    @Nullable
    @Min(0)
    Long getSequentialScansCount();

    @Nullable
    @Min(0)
    Long getSequentialScansFetchedRowsCount();

    @Nullable
    @Min(0)
    Long getIndexScansCount();

    @Nullable
    @Min(0)
    Long getIndexScansFetchedRowsCount();

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
    Long getHotUpdatedRowsCount();

    @Nullable
    @Min(0)
    Long getLiveRowsCount();

    @Nullable
    @Min(0)
    Long getDeadRowsCount();

    @Nullable
    Date getLastVacuum();

    @Nullable
    Date getLastAutoVacuum();

    @Nullable
    Date getLastAnalyze();

    @Nullable
    Date getLastAutoAnalyze();

    @Nullable
    @Min(0)
    Long getVacuumCount();

    @Nullable
    @Min(0)
    Long getAutoVacuumCount();

    @Nullable
    @Min(0)
    Long getAnalyzeCount();

    @Nullable
    @Min(0)
    Long getAutoAnalyzeCount();

    @Nullable
    @Min(0)
    Long getTableBlocksDiskRead();

    @Nullable
    @Min(0)
    Long getTableBlocksCacheRead();

    @Nullable
    @Min(0)
    Long getIndexesDiskRead();

    @Nullable
    @Min(0)
    Long getIndexesCacheRead();

    @Nullable
    @Min(0)
    Long getTableToastDiskRead();

    @Nullable
    @Min(0)
    Long getTableToastCacheRead();

    @Nullable
    @Min(0)
    Long getIndexesToastDiskRead();

    @Nullable
    @Min(0)
    Long getIndexesToastCacheRead();
}
