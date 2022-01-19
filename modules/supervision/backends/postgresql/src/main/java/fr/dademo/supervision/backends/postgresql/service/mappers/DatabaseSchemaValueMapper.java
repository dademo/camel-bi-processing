/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.service.mappers;

import fr.dademo.supervision.backends.model.database.resources.DatabaseSchemaDefaultImpl;

import java.util.Collections;
import java.util.function.Function;

/**
 * @author dademo
 */
public class DatabaseSchemaValueMapper implements Function<String, DatabaseSchemaDefaultImpl> {

    @Override
    public DatabaseSchemaDefaultImpl apply(String schemaName) {

        return DatabaseSchemaDefaultImpl.builder()
            .name(schemaName)
            .tables(Collections.emptyList())
            .views(Collections.emptyList())
            .indexes(Collections.emptyList())
            .build();
    }
}
