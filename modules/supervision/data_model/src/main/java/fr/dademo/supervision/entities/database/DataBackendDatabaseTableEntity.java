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
@Table(name = "data_backend_database_table_entity")
public class DataBackendDatabaseTableEntity implements Serializable {

    private static final long serialVersionUID = 2127603795918036607L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private DataBackendDatabaseSchemaEntity schema;

    @Nonnull
    @Size(min = 1)
    private String name;

    @Nonnull
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
    @Min(0)
    private Date lastVacuum;

    @Nullable
    @Min(0)
    private Date lastAutoVacuum;

    @Nullable
    @Min(0)
    private Date lastAnalyze;

    @Nullable
    @Min(0)
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