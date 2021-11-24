/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions.data_gouv_fr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author dademo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"owner", "quality", "extras"})
public class DataGouvFrDataSet {

    @Nullable
    private String id;

    @Nonnull
    private String title;

    @Nonnull
    private String uri;

    @Nullable
    private String acronym;

    @Nonnull
    private String description;

    @Nonnull
    private LocalDateTime createdAt;

    @Nullable
    private LocalDateTime deleted;

    @Nullable
    private LocalDateTime archived;

    @Nonnull
    private LocalDateTime lastModified;

    @Nonnull
    private LocalDateTime lastUpdate;

    @Nonnull
    private List<DataGouvFrDataSetResource> resources;

    @Nonnull
    private List<DataGouvFrDataSetResource> communityResources;

    @Nullable
    private List<DataGouvFrDataSetBadge> badges;

    @Nullable
    private Boolean featured;

    @Nonnull
    private DataGouvFrDataSetFrequency frequency;

    @Nullable
    private LocalDateTime frequencyDate;

    @Nullable
    private String license;

    @Nullable
    private Map<String, Integer> metrics;

    @Nonnull
    private String page;

    @Nullable
    @JsonProperty("private")
    private Boolean isPrivate;

    @Nonnull
    private String slug;

    @Nullable
    private DataGouvFrDataSetSpatialCoverage spatial;

    @Nullable
    private List<String> tags;

    @Nullable
    private DataGouvFrDataSetOrganization organization;

    @Nullable
    private DataGouvFrDataSetTemporalCoverage temporalCoverage;
}
