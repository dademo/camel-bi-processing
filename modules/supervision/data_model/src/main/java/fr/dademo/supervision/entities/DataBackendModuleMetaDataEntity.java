/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities;

import lombok.*;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author dademo
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Embeddable
public class DataBackendModuleMetaDataEntity implements Serializable {

    private static final long serialVersionUID = 5299679268268153054L;

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
