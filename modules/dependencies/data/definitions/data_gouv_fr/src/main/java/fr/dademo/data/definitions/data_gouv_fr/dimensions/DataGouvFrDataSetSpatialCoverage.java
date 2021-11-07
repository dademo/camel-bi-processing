package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataGouvFrDataSetSpatialCoverage {

    @Nullable
    private Geometry geom;

    @Nullable
    private String granularity;

    @Nullable
    private List<String> zones;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Geometry {
        @Nullable
        private String description;

        @Nonnull
        private List<String> coordinates;

        @Nonnull
        private Geometry.GeometryType type;

        @AllArgsConstructor
        public enum GeometryType {
            POINT("Point"),
            LINE_STRING("LineString"),
            POLYGON("Polygon"),
            MULTI_POINT("MultiPoint"),
            MULTI_LINE_STRING("MultiLineString"),
            MULTI_POLYGON("MultiPolygon");

            private final String value;

            @JsonValue
            public String getValue() {
                return value;
            }
        }
    }
}
