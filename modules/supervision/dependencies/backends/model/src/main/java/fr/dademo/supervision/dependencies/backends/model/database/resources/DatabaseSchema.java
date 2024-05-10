/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.database.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DatabaseSchema {

    @Nonnull
    @Size(min = 1, max = 255)
    String getName();

    @Nonnull
    @JsonDeserialize(as = List.class, contentAs = DatabaseTableDefaultImpl.class)
    Iterable<DatabaseTable> getTables();

    @Nonnull
    @JsonDeserialize(as = List.class, contentAs = DatabaseViewDefaultImpl.class)
    Iterable<DatabaseView> getViews();

    @Nonnull
    @JsonDeserialize(as = List.class, contentAs = DatabaseIndexDefaultImpl.class)
    Iterable<DatabaseIndex> getIndexes();
}
