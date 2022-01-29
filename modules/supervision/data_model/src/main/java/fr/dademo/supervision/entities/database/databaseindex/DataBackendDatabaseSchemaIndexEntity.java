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

package fr.dademo.supervision.entities.database.databaseindex;

import fr.dademo.supervision.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
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
@Table(
    name = "data_backend_database_schema_index",
    uniqueConstraints = {
        @UniqueConstraint(name = "data_backend_database_schema_index_uniq", columnNames = {
            "id_schema",
            "name",
            "table_name",
        })
    }
)
public class DataBackendDatabaseSchemaIndexEntity implements Serializable {

    private static final long serialVersionUID = -6623202159530184286L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_schema", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDatabaseSchemaEntity schema;

    @Nonnull
    @OneToMany(mappedBy = "index", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<DataBackendDatabaseSchemaIndexStatisticsEntity> statistics;

    @Nonnull
    @Column(name = "name", updatable = false)
    @Size(min = 1, max = 255)
    private String name;

    @Nonnull
    @Column(name = "table_name", updatable = false)
    @Size(min = 1, max = 255)
    private String tableName;
}