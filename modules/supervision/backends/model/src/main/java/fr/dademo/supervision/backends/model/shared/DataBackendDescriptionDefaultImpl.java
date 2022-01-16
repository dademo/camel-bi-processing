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

package fr.dademo.supervision.backends.model.shared;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.net.URL;

/**
 * Description of a supervised data backend.
 *
 * @author dademo
 */
@Data
@SuperBuilder
public abstract class DataBackendDescriptionDefaultImpl implements DataBackendDescription {

    @Nonnull
    private String backendProductName;

    @Nonnull
    private String backendProductVersion;

    @Nonnull
    private String backendName;

    @Nonnull
    private URL primaryUrl;

    @Size(min = 1)
    @Nonnull
    private Iterable<URL> nodeUrls;

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
