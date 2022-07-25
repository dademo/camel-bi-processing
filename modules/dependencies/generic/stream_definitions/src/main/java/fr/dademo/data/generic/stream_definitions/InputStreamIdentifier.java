/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.generic.stream_definitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@JsonIgnoreProperties({"description", "verboseDescription"})
public interface InputStreamIdentifier<T> {

    /**
     * Returning the flow identified by this class.
     * <p>
     * Type may depend on the implementation.
     *
     * @return the stream identified by this class
     */
    @Nonnull
    T getSource();

    void setSource(@Nonnull T source);

    @Nonnull
    default String getDescription() {
        return toString();
    }

    @Nonnull
    default String getVerboseDescription() {
        return getDescription();
    }
}
