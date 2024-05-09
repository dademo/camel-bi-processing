/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.entities.database.databasereplicationpeer;

import fr.dademo.supervision.dependencies.entities.BaseEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendDescriptionEntity;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;

import jakarta.validation.constraints.Size;
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
    name = "data_backend_database_replication_peer",
    uniqueConstraints = {
        @UniqueConstraint(name = "data_backend_database_replication_peer_uniq", columnNames = {
            "id_data_backend_description",
            "target_database",
            "application_name",
            "slot_name",
            "peer_address",
            "peer_host_name",
            "peer_port",
        })
    }
)
public class DataBackendDatabaseReplicationPeerEntity implements BaseEntity {

    private static final long serialVersionUID = -8192133668455329482L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_data_backend_description", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDescriptionEntity backendDescription;

    @Nonnull
    @OneToMany(mappedBy = "replicationPeer", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<DataBackendDatabaseReplicationPeerStatisticsEntity> replicationPeerStatistics;

    @Nullable
    @Column(name = "target_database", updatable = false)
    @Size(min = 1, max = 255)
    private String targetDatabase;

    @Nullable
    @Column(name = "use_name", updatable = false)
    @Size(min = 1, max = 255)
    private String useName;

    @Nullable
    @Column(name = "application_name", updatable = false)
    @Size(min = 1, max = 255)
    private String applicationName;

    @Nullable
    @Column(name = "slot_name", updatable = false)
    @Size(min = 1, max = 255)
    private String slotName;

    @Nullable
    @Column(name = "peer_address", updatable = false)
    @Size(min = 1, max = 255)
    private String peerAddress;

    @Nullable
    @Column(name = "peer_host_name", updatable = false)
    @Size(min = 1, max = 255)
    private String peerHostName;

    @Nullable
    @Column(name = "peer_port", updatable = false)
    private Long peerPort;
}
