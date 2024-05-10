/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.dademo.supervision.dependencies.backends.model.database.GlobalDatabaseDescriptionDefaultImpl;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescription;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendModuleMetaData;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendModuleMetaDataDefaultImpl;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dademo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataBackendStateFetchServiceExecutionResult {

    @Nonnull
    @JsonDeserialize(as = DataBackendModuleMetaDataDefaultImpl.class)
    private DataBackendModuleMetaData moduleMetaData;

    @Nonnull
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes({
        @JsonSubTypes.Type(value = GlobalDatabaseDescriptionDefaultImpl.class, name = "GlobalDatabaseDescription"),
    })
    private DataBackendDescription dataBackendDescription;

}
