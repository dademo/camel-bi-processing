/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.beans;

import fr.dademo.tools.cache.configuration.CacheConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static fr.dademo.tools.cache.configuration.CacheConfiguration.CONFIG_CACHE_BACKEND;

@Component
@ConditionalOnProperty(
    value = CacheConfiguration.CONFIGURATION_PREFIX + "." + CONFIG_CACHE_BACKEND,
    havingValue = CacheConfiguration.CacheBackend.CACHE_BACKEND_MINIO
)
public class CacheMinioEnabledConditional {
}
