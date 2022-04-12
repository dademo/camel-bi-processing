/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository.views;

import fr.dademo.supervision.dependencies.entities.database.databasetable.DataBackendDatabaseSchemaTableStatisticsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/**
 * @author dademo
 */
@Getter
@Builder
@AllArgsConstructor
public class DataBackendDatabaseSchemaTableStatisticsView {

    private final DataBackendDatabaseSchemaTableStatisticsEntity dataBackendDatabaseSchemaTableStatisticsEntity;

    private final Date timestamp;
}
