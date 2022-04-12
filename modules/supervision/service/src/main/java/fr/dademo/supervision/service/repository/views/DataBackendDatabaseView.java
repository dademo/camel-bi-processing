/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository.views;

import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author dademo
 */
@Getter
@Builder
@AllArgsConstructor
public class DataBackendDatabaseView {

    private final DataBackendDatabaseEntity dataBackendDatabaseEntity;

    private final Long dataBackendDescriptionId;

    private final Long databaseStatisticsCount;
    private final Long schemasCount;
}
