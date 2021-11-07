package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"extras", "schema"})
public class DataGouvFrDataSetResource {

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

    public static LocalDateTime dateTimeKeyExtractor(DataGouvFrDataSetResource v) {
        return Stream.of(
                        v.getLastModified(),
                        v.getPublished(),
                        v.getCreatedAt()
                ).filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @AllArgsConstructor
    public enum ResourceType {
        MAIN("main"),
        DOCUMENTATION("documentation"),
        UPDATE("update"),
        API("api"),
        CODE("code"),
        OTHER("other");

        private String value;

        @JsonValue
        public String getValue() {
            return value;
        }
    }

}
