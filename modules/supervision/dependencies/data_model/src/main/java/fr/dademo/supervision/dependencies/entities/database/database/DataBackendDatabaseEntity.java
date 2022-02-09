/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.entities.database.database;

import fr.dademo.supervision.dependencies.entities.BaseEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendDescriptionEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
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
    name = "data_backend_database",
    uniqueConstraints = {
        @UniqueConstraint(name = "data_backend_database_uniq", columnNames = {
            "id_data_backend_description",
            "name",
        })
    }
)
public class DataBackendDatabaseEntity implements BaseEntity {

    private static final long serialVersionUID = 2888789316495194833L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_data_backend_description", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDescriptionEntity backendDescription;

    @Nonnull
    @OneToMany(mappedBy = "database", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<DataBackendDatabaseStatisticsEntity> databaseStatistics;

    @Nonnull
    @OneToMany(mappedBy = "database", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<DataBackendDatabaseSchemaEntity> schemas;

    @Nullable
    @Size(min = 1, max = 255)
    @Column(name = "name", updatable = false)
    private String name;
}
