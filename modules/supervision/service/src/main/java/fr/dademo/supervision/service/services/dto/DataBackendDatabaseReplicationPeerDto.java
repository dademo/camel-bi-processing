/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.dto;

import lombok.*;

/**
 * @author dademo
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataBackendDatabaseReplicationPeerDto {

    // Primary key
    private Long id;

    // Links
    private Long dataBackendDatabaseId;

    // Primary values
    private String targetDatabase;
    private String useName;
    private String applicationName;
    private String slotName;
    private String peerAddress;
    private String peerHostName;
    private Long peerPort;

    // Linked entities count
    private Long statisticsCount;

}
