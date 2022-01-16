/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

/**
 * @author dademo
 */
@ConfigurationProperties(prefix = "module")
@Data
public class ModuleConfiguration {

    private URL dataSourceUrl;

    private String username;
    private String password;
}
