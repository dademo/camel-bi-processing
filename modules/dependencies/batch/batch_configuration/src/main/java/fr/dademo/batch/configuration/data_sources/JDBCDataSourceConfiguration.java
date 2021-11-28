/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.data_sources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.Duration;

/**
 * @author dademo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDBCDataSourceConfiguration {

    @NotBlank
    private String url;

    @Nullable
    private String username;

    @Nullable
    private String password;

    @Nullable
    private String driverClassName;

    @Min(0)
    private int minimumIdle = 0;

    @Min(1)
    private int maximumPoolSize = 10;

    @Min(1)
    private long connectionTimeoutMS = Duration.ofSeconds(30).toMillis();
}
