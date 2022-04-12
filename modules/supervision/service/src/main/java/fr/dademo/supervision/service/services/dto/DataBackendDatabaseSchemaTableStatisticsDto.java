/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.dto;

import lombok.*;

import java.util.Date;

/**
 * @author dademo
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataBackendDatabaseSchemaTableStatisticsDto {

    // Primary key
    private Long id;

    // Primary values
    private Date timestamp;
    private Long rowsCount;
    private Long totalSize;
    private Long sequentialScansCount;
    private Long sequentialScansFetchedRowsCount;
    private Long indexScansCount;
    private Long indexScansFetchedRowsCount;
    private Long insertedRowsCount;
    private Long updatedRowsCount;
    private Long deletedRowsCount;
    private Long hotUpdatedRowsCount;
    private Long liveRowsCount;
    private Long deadRowsCount;
    private Date lastVacuum;
    private Date lastAutoVacuum;
    private Date lastAnalyze;
    private Date lastAutoAnalyze;
    private Long vacuumCount;
    private Long autoVacuumCount;
    private Long analyzeCount;
    private Long autoAnalyzeCount;
    private Long tableBlocksDiskRead;
    private Long tableBlocksCacheRead;
    private Long indexesDiskRead;
    private Long indexesCacheRead;
    private Long tableToastDiskRead;
    private Long tableToastCacheRead;
    private Long indexesToastDiskRead;
    private Long indexesToastCacheRead;
}
