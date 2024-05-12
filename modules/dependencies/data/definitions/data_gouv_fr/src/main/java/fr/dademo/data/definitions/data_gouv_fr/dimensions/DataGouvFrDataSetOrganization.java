/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import fr.dademo.data.definitions.data_gouv_fr.serializer.ApifrDatesDeserializer;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataGouvFrDataSetOrganization {

    @Nonnull
    @JsonDeserialize(using = ApifrDatesDeserializer.class)
    LocalDateTime createdAt;

    @Nullable
    @JsonDeserialize(using = ApifrDatesDeserializer.class)
    LocalDateTime deleted;

    @Nullable
    @JsonDeserialize(using = ApifrDatesDeserializer.class)
    LocalDateTime lastModified;

    @Nonnull
    private String id;

    @Nonnull
    private String description;

    @Nonnull
    private String name;

    @Nonnull
    private String slug;

    @Nullable
    @JsonProperty("class")
    private String clazz;

    @Nullable
    private String acronym;

    @Nullable
    private String page;

    @Nullable
    private String uri;

    @Nullable
    private String url;

    @Nullable
    private List<DataGouvFrDataSetBadge> badges;

    @Nullable
    private String logo;

    @Nullable
    private String logoThumbnail;

    @Nullable
    private List<Member> members;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Member {

        @Nonnull
        private Role role;

        @Nullable
        private User user;


        @AllArgsConstructor
        public enum Role {
            EDITOR("editor"),
            ADMIN("admin");

            private final String value;

            @JsonValue
            public String getValue() {
                return value;
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class User {

            @Nonnull
            @JsonProperty("class")
            private String clazz;

            @Nonnull
            private String id;

            @Nonnull
            private String slug;

            @Nonnull
            private String uri;

            @Nullable
            private String avatar;

            @Nonnull
            private String avatarThumbnail;

            @Nonnull
            private String firstName;

            @Nonnull
            private String lastName;

            @Nonnull
            private String page;


        }
    }
}
