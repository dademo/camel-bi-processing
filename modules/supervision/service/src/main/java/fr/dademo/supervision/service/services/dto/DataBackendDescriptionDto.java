/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @author dademo
 */
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class DataBackendDescriptionDto extends DataBackendDescriptionLightDto {

    private String backendProductName;
    private String backendProductVersion;
    private Date startTime;
    private String backendStateExplanation;
    private Integer clusterSize;
    private Integer primaryCount;
    private Integer replicaCount;
    private Long sizeBytes;
    private Long effectiveSizeBytes;
    private Long availableSizeBytes;
    private Long backendStateExecutionsCount;
    private Long databasesCount;
}
