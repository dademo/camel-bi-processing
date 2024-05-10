/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.tools.object_mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Nonnull;

/**
 * @author dademo
 */
@FunctionalInterface
public interface ObjectMapperCustomizer {

    void customize(@Nonnull ObjectMapper mapper);
}
