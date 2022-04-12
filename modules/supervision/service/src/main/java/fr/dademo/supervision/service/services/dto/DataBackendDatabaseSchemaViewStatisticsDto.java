/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.dto;

import lombok.*;

import java.util.Date;

/**
 * @author dademo
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataBackendDatabaseSchemaViewStatisticsDto {

    // Primary key
    private Long id;

    // Primary values
    private Date timestamp;
    private Long rowsCount;
    private Long totalSize;
}
