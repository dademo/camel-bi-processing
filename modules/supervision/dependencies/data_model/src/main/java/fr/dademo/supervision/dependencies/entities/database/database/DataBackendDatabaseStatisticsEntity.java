/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.entities.database.database;

import fr.dademo.supervision.dependencies.entities.BaseEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.annotation.Nullable;

/**
 * @author dademo
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(
    name = "data_backend_database_statistics",
    uniqueConstraints = {
        @UniqueConstraint(name = "data_backend_database_statistics_uniq", columnNames = {
            "id_database",
            "id_backend_state_execution",
        })
    }
)
public class DataBackendDatabaseStatisticsEntity implements BaseEntity {

    private static final long serialVersionUID = -1134026069481113531L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_database", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDatabaseEntity database;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_backend_state_execution", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendStateExecutionEntity backendStateExecution;


    @Nullable
    @Min(0)
    @Column(name = "memory_usage_bytes", updatable = false)
    private Long memoryUsageBytes;

    @Nullable
    @Min(0)
    @Column(name = "cpu_usage_millicpu", updatable = false)
    private Long cpuUsageMilliCPU;

    @Nullable
    @Min(0)
    @Column(name = "commits_count", updatable = false)
    private Long commitCounts;

    @Nullable
    @Min(0)
    @Column(name = "rollbacks_count", updatable = false)
    private Long rollbacksCounts;

    @Nullable
    @Min(0)
    @Column(name = "buffer_blocks_read", updatable = false)
    private Long bufferBlocksRead;

    @Nullable
    @Min(0)
    @Column(name = "disk_blocks_read", updatable = false)
    private Long diskBlocksRead;

    @Nullable
    @Min(0)
    @Column(name = "returned_rows_count", updatable = false)
    private Long returnedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "fetched_rows_count", updatable = false)
    private Long fetchedRowsCount;

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
    @Column(name = "conflicts_count", updatable = false)
    private Long conflictsCount;

    @Nullable
    @Min(0)
    @Column(name = "dead_locks_count", updatable = false)
    private Long deadlocksCount;

    @Nullable
    @Column(name = "read_time_milliseconds", updatable = false)
    private Long readTimeMilliseconds;

    @Nullable
    @Column(name = "write_time_milliseconds", updatable = false)
    private Long writeTimeMilliseconds;
}
