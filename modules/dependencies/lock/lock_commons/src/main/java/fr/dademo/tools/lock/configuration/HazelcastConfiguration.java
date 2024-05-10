/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.configuration;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HazelcastConfiguration {

    private boolean localInstance = true;

    @Nullable
    private String clusterName;

    private boolean multicastEnabled = true;

    private boolean restEnabled = false;

    @Nullable
    private Integer cpMembersCount;

    @Nonnull
    private List<String> peers = Collections.emptyList();
}
