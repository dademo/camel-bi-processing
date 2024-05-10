/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.job.task.standalone.configuration;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "task")
public class TaskConfiguration {

    private DatasourceConfiguration datasource;

    @Data
    @NoArgsConstructor
    public static class DatasourceConfiguration {

        @Nonnull
        private String url;

        @Nonnull
        private String username;

        @Nonnull
        private String password;
    }
}
