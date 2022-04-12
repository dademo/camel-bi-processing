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
public class DataBackendDatabaseSchemaDto {

    // Primary key
    private Long id;

    // Links
    private Long dataBackendDatabaseId;

    // Primary values
    private String name;

    // Linked entities count
    private Long tablesCount;
    private Long schemasCount;
    private Long indexesCount;
}
