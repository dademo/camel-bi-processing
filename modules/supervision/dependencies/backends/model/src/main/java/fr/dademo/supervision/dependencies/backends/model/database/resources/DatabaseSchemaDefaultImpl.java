/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.database.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatabaseSchemaDefaultImpl implements DatabaseSchema {

    @Nonnull
    @Size(min = 1, max = 255)
    private String name;

    @Nonnull
    @JsonDeserialize(as = List.class, contentAs = DatabaseTableDefaultImpl.class)
    private Iterable<DatabaseTable> tables;

    @Nonnull
    @JsonDeserialize(as = List.class, contentAs = DatabaseViewDefaultImpl.class)
    private Iterable<DatabaseView> views;

    @Nonnull
    @JsonDeserialize(as = List.class, contentAs = DatabaseIndexDefaultImpl.class)
    private Iterable<DatabaseIndex> indexes;
}
