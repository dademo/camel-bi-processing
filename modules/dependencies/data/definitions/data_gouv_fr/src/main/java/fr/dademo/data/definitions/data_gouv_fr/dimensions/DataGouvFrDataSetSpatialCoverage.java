/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author dademo
 */
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
