/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.shared;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * Description of a supervised data backend.
 *
 * @author dademo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public abstract class DataBackendDescriptionDefaultImpl extends DataBackendDescriptionBaseDefaultImpl implements DataBackendDescription {

    @Nonnull
    @Size(min = 1, max = 255)
    private String backendProductName;

    @Nonnull
    @Size(min = 1, max = 50)
    private String backendProductVersion;

    @Nullable
    @Size(min = 1, max = 255)
    private String backendName;

    @Nonnull
    @Size(min = 1, max = 1000)
    private String primaryUrl;

    @Nonnull
    @JsonDeserialize(as = List.class, contentAs = DataBackendClusterNodeDefaultImpl.class)
    private Iterable<DataBackendClusterNode> backendNodes;

    @Nonnull
    private DataBackendState backendState;

    @Nullable
    @Size(min = 1)
    private String backendStateExplanation;

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
    private Date startTime;

    @Nullable
    @Min(0)
    private Long memoryUsageBytes;

    @Nullable
    @Min(0)
    private Long cpuUsageMilliCPU;
}
