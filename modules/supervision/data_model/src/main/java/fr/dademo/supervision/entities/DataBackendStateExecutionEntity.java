/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities;

import fr.dademo.supervision.entities.database.globaldatabase.DataBackendGlobalDatabaseEntity;
import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
@Table(name = "data_backend_state_execution")
public class DataBackendStateExecutionEntity implements Serializable {

    private static final long serialVersionUID = -4433439263893655592L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "execution", nullable = false)
    private Date timestamp;

    @OneToOne(optional = false, mappedBy = "backendStateExecution", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Nullable
    @ToString.Exclude
    private DataBackendGlobalDatabaseEntity globalDatabase;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_backend_module_meta_data", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendModuleMetaDataEntity dataBackendModuleMetaData;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_backend_description", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDescriptionEntity dataBackendDescription;
}