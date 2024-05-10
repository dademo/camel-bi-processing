/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import fr.dademo.data.definitions.DataSetResource;
import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSet;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author dademo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"extras", "schema"})
public class DataGouvFrDataSetResource implements DataSetResource {

    @Nullable
    private String id;
    @Nullable
    private String description;
    @Nonnull
    private String title;
    @Nonnull
    private String url;
    @Nonnull
    private ResourceType type;
    @Nullable
    private DataGouvFrDataSetResourceChecksum checksum;
    @Nonnull
    private LocalDateTime createdAt;
    @Nullable
    private LocalDateTime published;
    @Nullable
    private LocalDateTime lastModified;
    @Nullable
    @JsonProperty("filesize")
    private Integer fileSize;
    @Nonnull
    @JsonProperty("filetype")
    private String fileType;
    @Nonnull
    private String format;
    @Nullable
    private String latest;
    @Nullable
    private Map<String, Integer> metrics;
    @Nullable
    private String mime;
    @Nullable
    private String previewUrl;
    @JsonBackReference
    private DataGouvFrDataSet dataSet;

    public static LocalDateTime dateTimeKeyExtractor(DataGouvFrDataSetResource v) {
        return Stream.of(
                v.getLastModified(),
                v.getPublished(),
                v.getCreatedAt()
            ).filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    // Internal values and functions required for data set compatibility

    @Nullable
    public String getId() {
        // Internal technical ID doesn't exist as it always comes from an external provider
        return null;
    }

    @Nullable
    @Override
    public String getExternalId() {
        return id;
    }

    @Nonnull
    public String getName() {

        final var nameDate = Stream.of(
                lastModified,
                published,
                createdAt
            ).filter(Objects::nonNull)
            .findFirst()
            .map(date -> date.format(DateTimeFormatter.ISO_LOCAL_DATE))
            .orElse("-");
        return String.format("%s - %s - %s", dataSet.getTitle(), title, nameDate);
    }

    @Nullable
    @Override
    public String getParentId() {
        // Does not exist
        return null;
    }

    @Nullable
    @Override
    public String getSource() {
        return dataSet.getTitle();
    }

    @Nullable
    @Override
    public String getSourceSub() {
        return title;
    }

    @AllArgsConstructor
    public enum ResourceType {
        MAIN("main"),
        DOCUMENTATION("documentation"),
        UPDATE("update"),
        API("api"),
        CODE("code"),
        OTHER("other");

        private final String value;

        @JsonValue
        public String getValue() {
            return value;
        }
    }
}
