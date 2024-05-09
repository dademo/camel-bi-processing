/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.job.task.light.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author dademo
 */
@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "job.output")
public class JobOutputConfiguration {

    @Nonnull
    @NotBlank
    private String exchangeName;

    @Nullable
    @NotBlank
    private String routingKey;

    private boolean durable = false;

    private boolean autoDelete = false;

    private boolean delayed = false;

    private boolean internal = false;

    private Map<String, Object> arguments;
}
