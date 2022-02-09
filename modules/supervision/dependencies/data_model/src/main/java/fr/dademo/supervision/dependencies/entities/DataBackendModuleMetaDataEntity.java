/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.entities;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.annotation.Nonnull;
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
    name = "data_backend_module_meta_data",
    uniqueConstraints = {
        @UniqueConstraint(name = "data_backend_module_meta_data_uniq", columnNames = {
            "module_name",
            "module_title",
            "module_version",
            "module_vendor",
        })
    }
)
public class DataBackendModuleMetaDataEntity implements BaseEntity {

    private static final long serialVersionUID = 5299679268268153054L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Nonnull
    @OneToMany(mappedBy = "dataBackendModuleMetaData", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<DataBackendStateExecutionEntity> backendStateExecutions;

    @Nonnull
    @Size(min = 1, max = 255)
    @Column(name = "module_name", nullable = false, updatable = false)
    private String moduleName;

    @Nonnull
    @Size(min = 1, max = 255)
    @Column(name = "module_title", nullable = false, updatable = false)
    private String moduleTitle;

    @Nonnull
    @Size(min = 1, max = 50)
    @Column(name = "module_version", nullable = false, updatable = false)
    private String moduleVersion;

    @Nonnull
    @Size(min = 1, max = 255)
    @Column(name = "module_vendor", nullable = false, updatable = false)
    private String moduleVendor;
}
