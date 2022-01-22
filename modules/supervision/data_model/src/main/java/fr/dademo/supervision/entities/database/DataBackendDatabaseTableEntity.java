/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities.database;

import lombok.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "data_backend_database_table")
public class DataBackendDatabaseTableEntity implements Serializable {

    private static final long serialVersionUID = 2127603795918036607L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_schema")
    @ToString.Exclude
    private DataBackendDatabaseSchemaEntity schema;

    @Nonnull
    @Size(min = 1, max = 255)
    @Column(name = "name", updatable = false)
    private String name;

    @Nullable
    @Min(0)
    @Column(name = "rows_count", updatable = false)
    private Long rowsCount;

    @Nullable
    @Min(0)
    @Column(name = "total_size", updatable = false)
    private Long totalSize;

    @Nullable
    @Min(0)
    @Column(name = "sequential_scans_count", updatable = false)
    private Long sequentialScansCount;

    @Nullable
    @Min(0)
    @Column(name = "sequential_scans_fetched_rows_count", updatable = false)
    private Long sequentialScansFetchedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "index_scans_count", updatable = false)
    private Long indexScansCount;

    @Nullable
    @Min(0)
    @Column(name = "index_scans_fetched_rows_count", updatable = false)
    private Long indexScansFetchedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "inserted_rows_count", updatable = false)
    private Long insertedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "updated_rows_count", updatable = false)
    private Long updatedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "deleted_rows_count", updatable = false)
    private Long deletedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "hot_updated_rows_count", updatable = false)
    private Long hotUpdatedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "live_rows_count", updatable = false)
    private Long liveRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "dead_rows_count", updatable = false)
    private Long deadRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "last_vacuum", updatable = false)
    private Date lastVacuum;

    @Nullable
    @Min(0)
    @Column(name = "last_auto_vacuum", updatable = false)
    private Date lastAutoVacuum;

    @Nullable
    @Min(0)
    @Column(name = "last_analyze", updatable = false)
    private Date lastAnalyze;

    @Nullable
    @Min(0)
    @Column(name = "last_auto_analyze", updatable = false)
    private Date lastAutoAnalyze;

    @Nullable
    @Min(0)
    @Column(name = "vacuum_count", updatable = false)
    private Long vacuumCount;

    @Nullable
    @Min(0)
    @Column(name = "auto_vacuum_count", updatable = false)
    private Long autoVacuumCount;

    @Nullable
    @Min(0)
    @Column(name = "analyze_count", updatable = false)
    private Long analyzeCount;

    @Nullable
    @Min(0)
    @Column(name = "auto_analyze_count", updatable = false)
    private Long autoAnalyzeCount;

    @Nullable
    @Min(0)
    @Column(name = "table_blocks_disk_read", updatable = false)
    private Long tableBlocksDiskRead;

    @Nullable
    @Min(0)
    @Column(name = "table_blocks_cache_read", updatable = false)
    private Long tableBlocksCacheRead;

    @Nullable
    @Min(0)
    @Column(name = "indexes_disk_read", updatable = false)
    private Long indexesDiskRead;

    @Nullable
    @Min(0)
    @Column(name = "indexes_cache_read", updatable = false)
    private Long indexesCacheRead;

    @Nullable
    @Min(0)
    @Column(name = "table_toast_disk_read", updatable = false)
    private Long tableToastDiskRead;

    @Nullable
    @Min(0)
    @Column(name = "table_toast_cache_read", updatable = false)
    private Long tableToastCacheRead;

    @Nullable
    @Min(0)
    @Column(name = "indexes_toast_disk_read", updatable = false)
    private Long indexesToastDiskRead;

    @Nullable
    @Min(0)
    @Column(name = "indexes_toast_cache_read", updatable = false)
    private Long indexesToastCacheRead;
}
