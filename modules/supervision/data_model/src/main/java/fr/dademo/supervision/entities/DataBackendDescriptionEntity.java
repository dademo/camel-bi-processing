/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities;

import lombok.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Embeddable
public class DataBackendDescriptionEntity {

    @Nonnull
    private String backendProductName;

    @Nonnull
    private String backendProductVersion;

    @Nullable
    private String backendName;

    @Nullable
    private Date startTime;

    @Nonnull
    private String primaryUrl;

    // TODO
    //@Size(min = 1)
    //@Nonnull
    //private List<URL> nodeUrls;

    @Nonnull
    private String dataBackendState;

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