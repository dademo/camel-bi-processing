/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.helpers.data_gouv_fr.repository.query;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dademo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataGouvFrMetadataServiceMetadataQueryOptions {

    @Nullable
    String query;

    @Nullable
    List<String> facets;

    @Nullable
    String tag;

    @Nullable
    String badge;

    @Nullable
    String organization;

    @Nullable
    String owner;

    @Nullable
    String license;

    @Nullable
    String geozone;

    @Nullable
    String granularity;

    @Nullable
    String format;

    @Nullable
    String schema;

    @Nullable
    String schemaVersion;

    @Nullable
    String resourceType;

    @Nullable
    String reuses;

    @Nullable
    String temporalCoverageStart;

    @Nullable
    String temporalCoverageEnd;

    @Nullable
    Boolean featured;

    @Nullable
    String sort;

    @Nullable
    Integer page;

    @Nullable
    Integer pageSize;

    public Map<String, String> getUrlParams() {

        var parameters = new HashMap<String, String>();

        parameters.put("q", query);
        parameters.put("facets", String.join(",", Optional.ofNullable(facets).orElse(Collections.emptyList())));
        parameters.put("tag", tag);
        parameters.put("badge", badge);
        parameters.put("organization", organization);
        parameters.put("owner", owner);
        parameters.put("license", license);
        parameters.put("geozone", geozone);
        parameters.put("granularity", granularity);
        parameters.put("format", format);
        parameters.put("schema", schema);
        parameters.put("schema_version", schemaVersion);
        parameters.put("resource_type", resourceType);
        parameters.put("reuses", reuses);
        parameters.put("temporalCoverage", temporalCoverageStart + "-" + temporalCoverageEnd);
        Optional.ofNullable(featured).ifPresent(f -> parameters.put("featured", f.toString()));
        parameters.put("sort", sort);
        parameters.put("page", Optional.ofNullable(page).map(Object::toString).orElse(""));
        parameters.put("page_size", Optional.ofNullable(pageSize).map(Object::toString).orElse(""));

        return parameters.entrySet().stream()
            .filter(kv -> Strings.isNotEmpty(kv.getValue()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            ));
    }
}
