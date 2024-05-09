/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.entities.database.databaseview;

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
    name = "data_backend_database_schema_view_statistics",
    uniqueConstraints = {
        @UniqueConstraint(name = "data_backend_database_schema_view_statistics_uniq", columnNames = {
            "id_view",
            "id_backend_state_execution",
        })
    }
)
public class DataBackendDatabaseSchemaViewStatisticsEntity implements BaseEntity {

    private static final long serialVersionUID = 148773153132763617L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_view", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDatabaseSchemaViewEntity view;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_backend_state_execution", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendStateExecutionEntity backendStateExecution;

    @Nullable
    @Column(name = "rows_count", updatable = false)
    @Min(0)
    private Long rowsCount;

    @Nullable
    @Column(name = "total_size", updatable = false)
    @Min(0)
    private Long totalSize;
}
