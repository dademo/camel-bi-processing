/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.entities;

import fr.dademo.supervision.dependencies.entities.database.connection.DataBackendDatabaseConnectionEntity;
import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseEntity;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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
    name = "data_backend_description",
    uniqueConstraints = {
        @UniqueConstraint(name = "data_backend_description_uniq", columnNames = {
            "primary_url",
            "start_time",
            "backend_name",
            "backend_product_name",
            "backend_product_version",
            "backend_state",
            "backend_state_explanation",
        })
    }
)
public class DataBackendDescriptionEntity implements BaseEntity {

    private static final long serialVersionUID = 5367781118588957675L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Nonnull
    @OneToMany(mappedBy = "dataBackendDescription", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<DataBackendStateExecutionEntity> backendStateExecutions;

    @Nonnull
    @OneToMany(mappedBy = "backendDescription", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<DataBackendDatabaseEntity> databases;

    @Nonnull
    @OneToMany(mappedBy = "backendDescription", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<DataBackendDatabaseConnectionEntity> databaseConnections;

    @Nonnull
    @Size(min = 1, max = 255)
    @Column(name = "backend_product_name", nullable = false, updatable = false)
    private String backendProductName;

    @Nonnull
    @Size(min = 1, max = 50)
    @Column(name = "backend_product_version", nullable = false, updatable = false)
    private String backendProductVersion;

    @Nullable
    @Size(min = 1, max = 255)
    @Column(name = "backend_name", updatable = false)
    private String backendName;

    @Nullable
    @Column(name = "start_time", updatable = false)
    private Date startTime;

    @Nonnull
    @Column(name = "primary_url", updatable = false)
    private String primaryUrl;

    // TODO
    //@Size(min = 1)
    //@Nonnull
    //@Column(name = "node_urls", updatable = false)
    //private List<URL> nodeUrls;

    @Nonnull
    @Column(name = "backend_state", nullable = false, updatable = false)
    private String backendState;

    @Nullable
    @Size(min = 1)
    @Column(name = "backend_state_explanation", updatable = false)
    private String backendStateExplanation;

    @Nullable
    @Min(1)
    @Column(name = "cluster_size", updatable = false)
    private Integer clusterSize;

    @Nullable
    @Min(1)
    @Column(name = "primary_count", updatable = false)
    private Integer primaryCount;

    @Nullable
    @Min(0)
    @Column(name = "replica_count", updatable = false)
    private Integer replicaCount;

    @Nullable
    @Min(0)
    @Column(name = "size_bytes", updatable = false)
    private Long sizeBytes;

    @Nullable
    @Min(0)
    @Column(name = "effective_size_bytes", updatable = false)
    private Long effectiveSizeBytes;

    @Nullable
    @Min(0)
    @Column(name = "available_size_bytes", updatable = false)
    private Long availableSizeBytes;
}