/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.entities.database.databaseindex;

import fr.dademo.supervision.dependencies.entities.BaseEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.annotation.Nullable;
import javax.persistence.*;

import jakarta.validation.constraints.Min;

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
    name = "data_backend_database_schema_index_statistics",
    uniqueConstraints = {
        @UniqueConstraint(name = "data_backend_database_schema_index_statistics_uniq", columnNames = {
            "id_index",
            "id_backend_state_execution",
        })
    }
)
public class DataBackendDatabaseSchemaIndexStatisticsEntity implements BaseEntity {

    private static final long serialVersionUID = -7088606431247652530L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_index", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDatabaseSchemaIndexEntity index;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_backend_state_execution", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendStateExecutionEntity backendStateExecution;

    @Nullable
    @Column(name = "index_scans_count", updatable = false)
    @Min(0)
    private Long indexScansCount;

    @Nullable
    @Column(name = "returned_rows_count", updatable = false)
    @Min(0)
    private Long returnedRowsCount;

    @Nullable
    @Column(name = "fetched_rows_count", updatable = false)
    @Min(0)
    private Long fetchedRowsCount;
}
