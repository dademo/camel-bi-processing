/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities;

import lombok.*;

import javax.annotation.Nonnull;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;

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
public class DataBackendModuleMetaDataEntity {

    @Nonnull
    @Min(1)
    private String moduleName;

    @Nonnull
    @Min(1)
    private String moduleTitle;

    @Nonnull
    @Min(1)
    private String moduleVersion;

    @Nonnull
    @Min(1)
    private String moduleVendor;
}
