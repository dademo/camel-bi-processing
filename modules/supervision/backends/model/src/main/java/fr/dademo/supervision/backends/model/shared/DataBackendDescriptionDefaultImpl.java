/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.model.shared;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Description of a supervised data backend.
 *
 * @author dademo
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public abstract class DataBackendDescriptionDefaultImpl extends DataBackendDescriptionBaseDefaultImpl implements DataBackendDescription {

    @Nonnull
    private String backendProductName;

    @Nonnull
    private String backendProductVersion;

    @Nullable
    private String backendName;

    @Nonnull
    private String primaryUrl;

    @Size(min = 1)
    @Nonnull
    private Iterable<String> nodeUrls;

    @Nonnull
    private DataBackendState dataBackendState;

    @Nullable
    @Size(min = 1)
    private String dataBackendStateExplanation;

    @Nullable
    @Min(1)
    private Integer clusterSize;

    @Nullable
    @Min(1)
    private Integer primaryCount;

    @Nullable
    @Min(0)
    private Integer replicaCount;
}
