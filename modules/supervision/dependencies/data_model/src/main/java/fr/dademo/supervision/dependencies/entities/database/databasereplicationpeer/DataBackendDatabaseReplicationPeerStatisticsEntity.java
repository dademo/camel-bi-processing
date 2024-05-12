/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.entities.database.databasereplicationpeer;

import fr.dademo.supervision.dependencies.entities.BaseEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;

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
    name = "data_backend_database_replication_peer_statistics",
    uniqueConstraints = {
        @UniqueConstraint(name = "data_backend_database_replication_peer_statistics_uniq", columnNames = {
            "id_replication_peer",
            "id_backend_state_execution",
        })
    }
)
public class DataBackendDatabaseReplicationPeerStatisticsEntity implements BaseEntity {

    @Serial
    private static final long serialVersionUID = -4568634439423236389L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_replication_peer", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDatabaseReplicationPeerEntity replicationPeer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_backend_state_execution", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendStateExecutionEntity backendStateExecution;

    @Nonnull
    @Column(name = "is_primary", updatable = false)
    private Boolean isPrimary;

    @Nonnull
    @Column(name = "is_active", updatable = false)
    private Boolean isActive;

    @Nullable
    @Column(name = "status", updatable = false)
    @Size(min = 1, max = 255)
    private String status;

    @Nullable
    @Column(name = "replication_pid", updatable = false)
    private Long replicationPID;

    @Nullable
    @Column(name = "state", updatable = false)
    @Size(min = 1, max = 50)
    private String state;

    @Nullable
    @Column(name = "sync_state", updatable = false)
    @Size(min = 1, max = 255)
    private String syncState;

    @Nullable
    @Column(name = "replication_delay_milliseconds", updatable = false)
    private Long replicationDelayMilliseconds;

    @Nullable
    @Column(name = "sending_delay_size", updatable = false)
    private Long sendingDelaySize;

    @Nullable
    @Column(name = "receiving_delay_size", updatable = false)
    private Long receivingDelaySize;

    @Nullable
    @Column(name = "relaying_delay_size", updatable = false)
    private Long replayingDelaySize;

    @Nullable
    @Column(name = "total_delay_size", updatable = false)
    private Long totalDelaySize;
}
