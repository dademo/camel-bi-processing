/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.database.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatabaseTableDefaultImpl implements DatabaseTable {

    @Nonnull
    @Size(min = 1, max = 255)
    private String name;

    @Nullable
    @Min(0)
    private Long rowsCount;

    @Nullable
    @Min(0)
    private Long totalSize;

    @Nullable
    @Min(0)
    private Long sequentialScansCount;

    @Nullable
    @Min(0)
    private Long sequentialScansFetchedRowsCount;

    @Nullable
    @Min(0)
    private Long indexScansCount;

    @Nullable
    @Min(0)
    private Long indexScansFetchedRowsCount;

    @Nullable
    @Min(0)
    private Long insertedRowsCount;

    @Nullable
    @Min(0)
    private Long updatedRowsCount;

    @Nullable
    @Min(0)
    private Long deletedRowsCount;

    @Nullable
    @Min(0)
    private Long hotUpdatedRowsCount;

    @Nullable
    @Min(0)
    private Long liveRowsCount;

    @Nullable
    @Min(0)
    private Long deadRowsCount;

    @Nullable
    private Date lastVacuum;

    @Nullable
    private Date lastAutoVacuum;

    @Nullable
    private Date lastAnalyze;

    @Nullable
    private Date lastAutoAnalyze;

    @Nullable
    @Min(0)
    private Long vacuumCount;

    @Nullable
    @Min(0)
    private Long autoVacuumCount;

    @Nullable
    @Min(0)
    private Long analyzeCount;

    @Nullable
    @Min(0)
    private Long autoAnalyzeCount;

    @Nullable
    @Min(0)
    private Long tableBlocksDiskRead;

    @Nullable
    @Min(0)
    private Long tableBlocksCacheRead;

    @Nullable
    @Min(0)
    private Long indexesDiskRead;

    @Nullable
    @Min(0)
    private Long indexesCacheRead;

    @Nullable
    @Min(0)
    private Long tableToastDiskRead;

    @Nullable
    @Min(0)
    private Long tableToastCacheRead;

    @Nullable
    @Min(0)
    private Long indexesToastDiskRead;

    @Nullable
    @Min(0)
    private Long indexesToastCacheRead;
}
