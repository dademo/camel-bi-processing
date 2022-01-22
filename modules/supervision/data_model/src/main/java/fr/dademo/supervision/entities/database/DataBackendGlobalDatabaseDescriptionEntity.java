/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities.database;

import fr.dademo.supervision.entities.DataBackendStateExecutionEntity;
import lombok.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "data_backend_global_database_description")
public class DataBackendGlobalDatabaseDescriptionEntity implements Serializable {

    private static final long serialVersionUID = 2888789316495194833L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_backend_state_execution")
    @ToString.Exclude
    private DataBackendStateExecutionEntity backendStateExecution;

    @Nonnull
    @OneToMany(mappedBy = "globalDatabase", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DataBackendDatabaseConnectionEntity> connections;

    @Nonnull
    @OneToMany(mappedBy = "globalDatabase", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DataBackendDatabaseDescriptionEntity> databases;

    @Nullable
    @Column(name = "start_time", updatable = false)
    private Date startTime;
}
