/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities.database.databaseschema;

import fr.dademo.supervision.entities.database.database.DataBackendDatabaseEntity;
import fr.dademo.supervision.entities.database.databaseindex.DataBackendDatabaseSchemaIndexEntity;
import fr.dademo.supervision.entities.database.databasetable.DataBackendDatabaseSchemaTableEntity;
import fr.dademo.supervision.entities.database.databaseview.DataBackendDatabaseSchemaViewEntity;
import lombok.*;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
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
@Table(name = "data_backend_database_schema")
public class DataBackendDatabaseSchemaEntity implements Serializable {

    private static final long serialVersionUID = 7554858801534261567L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_database", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDatabaseEntity database;

    @Nonnull
    @Size(min = 1, max = 255)
    @Column(name = "name", updatable = false)
    private String name;

    @Nonnull
    @OneToMany(mappedBy = "schema", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<DataBackendDatabaseSchemaTableEntity> tables;

    @Nonnull
    @OneToMany(mappedBy = "schema", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<DataBackendDatabaseSchemaViewEntity> views;

    @Nonnull
    @OneToMany(mappedBy = "schema", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<DataBackendDatabaseSchemaIndexEntity> indexes;
}