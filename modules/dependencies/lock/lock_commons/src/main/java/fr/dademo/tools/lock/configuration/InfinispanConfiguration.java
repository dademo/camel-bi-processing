/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.configuration;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfinispanConfiguration {

    @Nullable
    private String clusterName;

    @Nullable
    private String nodeName;

    @Nullable
    private String cacheName;

    @Nullable
    private String jGroupsFile;

    private List<String> peers = Collections.emptyList();
}
