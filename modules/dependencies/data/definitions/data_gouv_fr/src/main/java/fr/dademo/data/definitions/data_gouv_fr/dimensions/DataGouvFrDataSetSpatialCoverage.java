package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGouvFrDataSetSpatialCoverage {

    @Nullable
    private Geometry geom;

    @Nullable
    private String granularity;

    @Nullable
    private List<String> zones;

    @Data
    public static class Geometry {
        @Nullable
        private String description;

        @Nonnull
        private List<String> coordinates;

        @Nonnull
        private Geometry.GeometryType type;

        @AllArgsConstructor
        @Getter
        public enum GeometryType {
            POINT("Point"),
            LINE_STRING("LineString"),
            POLYGON("Polygon"),
            MULTI_POINT("MultiPoint"),
            MULTI_LINE_STRING("MultiLineString"),
            MULTI_POLYGON("MultiPolygon");

            private final String value;
        }
    }
}
