/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.generic.stream_definitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@JsonIgnoreProperties({"uniqueIdentifier"})
public interface Cacheable extends Serializable {

    /**
     * Get an unique identifier that should not change in order to cache the value and retrieve it easily.
     *
     * @return An unique identifier to identify this object.
     */
    @Nonnull
    @NotBlank
    String getUniqueIdentifier();
}
