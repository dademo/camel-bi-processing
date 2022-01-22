/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities.database;

import lombok.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "data_backend_database_index")
public class DataBackendDatabaseIndexEntity implements Serializable {

    private static final long serialVersionUID = -6623202159530184286L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_schema", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDatabaseSchemaEntity schema;

    @Nonnull
    @Column(name = "name", updatable = false)
    @Size(min = 1, max = 255)
    private String name;

    @Nonnull
    @Column(name = "table_name", updatable = false)
    @Size(min = 1, max = 255)
    private String tableName;

    @Nullable
    @Column(name = "index_scans_count", updatable = false)
    @Min(0)
    private Long indexScansCount;

    @Nullable
    @Column(name = "returned_rows_count", updatable = false)
    @Min(0)
    private Long returnedRowsCount;

    @Nullable
    @Column(name = "fetched_rows_count", updatable = false)
    @Min(0)
    private Long fetchedRowsCount;
}