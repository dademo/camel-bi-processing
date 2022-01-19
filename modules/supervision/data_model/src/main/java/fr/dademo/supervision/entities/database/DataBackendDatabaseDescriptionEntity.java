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
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "data_backend_database_description_entity")
public class DataBackendDatabaseDescriptionEntity implements Serializable {

    private static final long serialVersionUID = 2888789316495194833L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DATABASE")
    @ToString.Exclude
    private DataBackendGlobalDatabaseDescriptionEntity globalDatabase;

    @Nonnull
    @OneToMany(mappedBy = "database", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DataBackendDatabaseSchemaEntity> databaseSchemas;

    @Nullable
    @Size(min = 1)
    private String name;

    @Nullable
    @Min(0)
    private Long memoryUsageBytes;

    @Nullable
    @Min(0)
    private Long cpuUsageMilliCPU;

    @Nullable
    @Min(0)
    private Long commitCounts;

    @Nullable
    @Min(0)
    private Long rollbackCounts;

    @Nullable
    @Min(0)
    private Long bufferBlocksRead;

    @Nullable
    @Min(0)
    private Long diskBlocksRead;

    @Nullable
    @Min(0)
    private Long returnedRowsCount;

    @Nullable
    @Min(0)
    private Long fetchedRowsCount;

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
    private Long conflictsCount;

    @Nullable
    @Min(0)
    private Long deadlocksCount;

    @Nullable
    private Duration readTime;

    @Nullable
    private Duration writeTime;
}