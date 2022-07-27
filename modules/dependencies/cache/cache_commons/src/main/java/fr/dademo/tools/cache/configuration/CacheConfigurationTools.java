/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.net.URI;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheConfigurationTools {

    @SneakyThrows
    static URI ensureURIIsADirectory(URI sourceURI) {

        if (!(sourceURI.getPath()).endsWith("/")) {
            return URI.create(sourceURI + "/");
        } else {
            return sourceURI;
        }
    }
}
