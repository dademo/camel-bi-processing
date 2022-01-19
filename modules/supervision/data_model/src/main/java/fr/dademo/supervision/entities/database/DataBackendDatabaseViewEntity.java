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
@Table(name = "data_backend_database_view_entity")
public class DataBackendDatabaseViewEntity implements Serializable {

    private static final long serialVersionUID = 137597439122592056L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private DataBackendDatabaseSchemaEntity schema;

    @Nonnull
    @Size(min = 1)
    private String name;

    @Nullable
    @Min(0)
    private Long rowsCount;

    @Nullable
    @Min(0)
    private Long totalSize;

    @Nullable
    private String expression;
}