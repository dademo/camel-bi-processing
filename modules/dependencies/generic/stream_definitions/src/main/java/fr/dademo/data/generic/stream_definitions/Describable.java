/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.generic.stream_definitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nonnull;

import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties({"description", "verboseDescription"})
public interface Describable {

    @Nonnull
    @NotBlank
    default String getDescription() {
        return toString();
    }

    @Nonnull
    @NotBlank
    default String getVerboseDescription() {
        return getDescription();
    }
}
