/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;

/**
 * @author dademo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class DataBackendDescriptionBaseDefaultImpl implements DataBackendDescriptionBase {

    @Nullable
    @Min(0)
    private Long sizeBytes;

    @Nullable
    @Min(0)
    private Long effectiveSizeBytes;

    @Nullable
    @Min(0)
    private Long availableSizeBytes;
}
