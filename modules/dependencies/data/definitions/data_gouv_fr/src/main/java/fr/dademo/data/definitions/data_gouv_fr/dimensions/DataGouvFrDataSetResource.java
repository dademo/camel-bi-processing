package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"extras", "schema"})
public class DataGouvFrDataSetResource {

    @Nullable
    private String id;

    @Nullable
    private String description;

    @Nullable
    private DataGouvFrDataSetResourceChecksum checksum;

    @Nonnull
    private LocalDateTime createdAt;

    @Nullable
    private LocalDateTime published;

    @Nullable
    private LocalDateTime lastModified;

    @Nullable
    private Integer fileSize;

    @Nullable
    private String fileType;

    @Nullable
    private String format;

    @Nullable
    private String latest;

    @Nullable
    private Map<String, Integer> metrics;

    @Nullable
    private String mime;

    @Nullable
    private String previewUrl;

    @Nonnull
    private String title;

    @Nonnull
    private String url;

}
