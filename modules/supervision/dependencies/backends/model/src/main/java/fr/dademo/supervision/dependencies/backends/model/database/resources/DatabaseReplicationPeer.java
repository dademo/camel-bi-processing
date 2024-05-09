/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.database.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;
import java.net.InetAddress;
import java.time.Duration;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DatabaseReplicationPeer {

    @Nonnull
    Boolean getIsActive();

    @Nonnull
    Boolean getIsPrimary();

    @Nullable
    @Size(min = 1, max = 255)
    String getTargetDatabase();

    @Nullable
    @Size(min = 1, max = 255)
    String getStatus();

    @Nullable
    Long getReplicationPID();

    @Nullable
    @Size(min = 1, max = 255)
    String getUseName();

    @Nullable
    @Size(min = 1, max = 255)
    String getApplicationName();

    @Nullable
    @Size(min = 1, max = 255)
    String getSlotName();

    @Nullable
    InetAddress getPeerAddress();

    @Nullable
    @Size(min = 1, max = 255)
    String getPeerHostName();

    @Nullable
    Long getPeerPort();

    @Nullable
    @Size(min = 1, max = 255)
    String getState();

    @Nullable
    @Size(min = 1, max = 255)
    String getSyncState();

    @Nullable
    Duration getReplicationDelay();

    @Nullable
    Long getSendingDelaySize();

    @Nullable
    Long getReceivingDelaySize();

    @Nullable
    Long getReplayingDelaySize();

    @Nullable
    Long getTotalDelaySize();
}
