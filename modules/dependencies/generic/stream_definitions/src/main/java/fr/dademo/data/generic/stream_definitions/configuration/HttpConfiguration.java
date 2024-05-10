/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.generic.stream_definitions.configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static fr.dademo.data.generic.stream_definitions.configuration.HttpConfiguration.CONFIGURATION_PREFIX;

/**
 * @author dademo
 */
@Configuration
@ConfigurationProperties(prefix = CONFIGURATION_PREFIX)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpConfiguration {

    public static final String CONFIGURATION_PREFIX = "http";
    public static final int DEFAULT_CORE_POOL_SIZE = 5;
    public static final int DEFAULT_MAX_POOL_SIZE = 50;
    public static final int DEFAULT_KEEP_ALIVE_TIME_SECONDS = 30;

    @Min(0)
    private long connectTimeoutSeconds = 0;

    @Min(0)
    private HttpConfigurationExecutor executorConfiguration = null;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HttpConfigurationExecutor {

        @Min(0)
        @Max(Integer.MAX_VALUE)
        private int corePoolSize = DEFAULT_CORE_POOL_SIZE;

        @Min(1)
        @Max(Integer.MAX_VALUE)
        private int maximumPoolSize = DEFAULT_MAX_POOL_SIZE;

        @Min(0)
        @Max(Integer.MAX_VALUE)
        private int keepAliveTimeSeconds = DEFAULT_KEEP_ALIVE_TIME_SECONDS;
    }
}
