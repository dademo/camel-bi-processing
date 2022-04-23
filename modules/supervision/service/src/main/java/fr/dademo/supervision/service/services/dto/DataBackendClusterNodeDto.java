/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.dto;

import lombok.*;

import java.util.Date;
import java.util.List;


/**
 * @author dademo
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataBackendClusterNodeDto {

    // Primary key
    private Long id;

    // Primary values
    @With
    private List<Date> timestamps;
    private String url;
    private String role;
}
