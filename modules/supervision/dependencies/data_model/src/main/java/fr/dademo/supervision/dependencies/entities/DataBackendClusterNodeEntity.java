/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.entities;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

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
    name = "data_backend_cluster_node",
    uniqueConstraints = {
        @UniqueConstraint(name = "data_backend_cluster_node_uniq", columnNames = {
            "id_data_backend_description",
            "url",
        })
    }
)
public class DataBackendClusterNodeEntity implements BaseEntity {

    private static final long serialVersionUID = -8516888524176793024L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_data_backend_description", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDescriptionEntity backendDescription;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
        name = "data_backend_cluster_node__state_execution",
        joinColumns = {
            @JoinColumn(name = "id_data_backend_cluster_node", referencedColumnName = "id", nullable = false),
        },
        inverseJoinColumns = {
            @JoinColumn(name = "id_backend_state_execution", referencedColumnName = "id", nullable = false),
        }
    )
    @ToString.Exclude
    private List<DataBackendStateExecutionEntity> backendStateExecutions;

    @Nonnull
    @Size(min = 1, max = 1000)
    @Column(name = "url", updatable = false)
    private String url;

    @Nullable
    @Size(min = 1, max = 255)
    @Column(name = "role", updatable = false)
    private String role;
}
