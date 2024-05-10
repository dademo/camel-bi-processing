/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.database.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.InetAddress;
import java.time.Duration;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatabaseReplicationPeerDefaultImpl implements DatabaseReplicationPeer {

    @Nonnull
    private Boolean isActive;

    @Nonnull
    private Boolean isPrimary;

    @Nullable
    @Size(min = 1, max = 255)
    private String targetDatabase;

    @Nullable
    @Size(min = 1, max = 255)
    private String status;

    @Nullable
    private Long replicationPID;

    @Nullable
    @Size(min = 1, max = 255)
    private String useName;

    @Nullable
    @Size(min = 1, max = 255)
    private String applicationName;

    @Nullable
    @Size(min = 1, max = 255)
    private String slotName;

    @Nullable
    private InetAddress peerAddress;

    @Nullable
    @Size(min = 1, max = 255)
    private String peerHostName;

    @Nullable
    private Long peerPort;

    @Nullable
    @Size(min = 1, max = 50)
    private String state;

    @Nullable
    @Size(min = 1, max = 255)
    private String syncState;

    @Nullable
    private Duration replicationDelay;

    @Nullable
    private Long sendingDelaySize;

    @Nullable
    private Long receivingDelaySize;

    @Nullable
    private Long replayingDelaySize;

    @Nullable
    private Long totalDelaySize;
}
