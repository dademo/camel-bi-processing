/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository;

import jakarta.annotation.Nonnull;

import java.net.http.HttpRequest;

/**
 * @author dademo
 */
@FunctionalInterface
public interface QueryCustomizer {

    @Nonnull
    HttpRequest.Builder customizeRequest(@Nonnull HttpRequest.Builder request);
}
