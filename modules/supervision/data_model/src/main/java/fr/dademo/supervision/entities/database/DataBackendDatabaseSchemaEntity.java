/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities.database;

import lombok.*;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "data_backend_database_schema")
public class DataBackendDatabaseSchemaEntity implements Serializable {

    private static final long serialVersionUID = 7554858801534261567L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_database")
    @ToString.Exclude
    private DataBackendDatabaseDescriptionEntity database;

    @Nonnull
    @OneToMany(mappedBy = "schema", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DataBackendDatabaseTableEntity> tables;

    @Nonnull
    @OneToMany(mappedBy = "schema", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DataBackendDatabaseViewEntity> views;

    @Nonnull
    @OneToMany(mappedBy = "schema", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DataBackendDatabaseIndexEntity> indexes;

    @Nonnull
    @Size(min = 1, max = 255)
    @Column(name = "name", updatable = false)
    private String name;
}