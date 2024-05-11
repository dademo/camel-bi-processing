/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions.data_gouv_fr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import fr.dademo.data.definitions.DataSet;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.*;
import fr.dademo.data.definitions.data_gouv_fr.serializer.ApifrDatesDeserializer;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@JsonIgnoreProperties({"owner", "quality", "extras", "contact_point", "harvest", "internal"})
public class DataGouvFrDataSet implements DataSet {

    public static final String DATE_FORMAT_WITH_MICROSECONDS = "yyyy-MM-dd'T'HH:mm:ss.nnnnnnZZZZZ";
    public static final String DATE_FORMAT_SIMPLE = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";

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
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_WITH_MICROSECONDS)
    @JsonDeserialize(using = ApifrDatesDeserializer.class)
    private LocalDateTime createdAt;

    @Nullable
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_WITH_MICROSECONDS)
    @JsonDeserialize(using = ApifrDatesDeserializer.class)
    private LocalDateTime deleted;

    @Nullable
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_WITH_MICROSECONDS)
    @JsonDeserialize(using = ApifrDatesDeserializer.class)
    private LocalDateTime archived;

    @Nonnull
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_WITH_MICROSECONDS)
    @JsonDeserialize(using = ApifrDatesDeserializer.class)
    private LocalDateTime lastModified;

    @Nonnull
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_SIMPLE)
    @JsonDeserialize(using = ApifrDatesDeserializer.class)
    private LocalDateTime lastUpdate;

    @Nonnull
    @JsonManagedReference
    private List<DataGouvFrDataSetResource> resources;

    @Nullable
    private DataGouvFrDataSetSchema schema;

    @Nonnull
    private List<DataGouvFrDataSetResource> communityResources;

    @Nullable
    private List<DataGouvFrDataSetBadge> badges;

    @Nullable
    private Boolean featured;

    @Nonnull
    private DataGouvFrDataSetFrequency frequency;

    @Nullable
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_WITH_MICROSECONDS)
    @JsonDeserialize(using = ApifrDatesDeserializer.class)
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
