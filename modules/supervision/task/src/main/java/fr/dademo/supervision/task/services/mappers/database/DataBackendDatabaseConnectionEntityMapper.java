/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services.mappers.database;

import fr.dademo.supervision.backends.model.database.resources.DatabaseConnection;
import fr.dademo.supervision.entities.database.DataBackendDatabaseConnectionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.annotation.Nullable;
import java.net.InetAddress;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseConnectionEntityMapper {

    DataBackendDatabaseConnectionEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseConnectionEntityMapper.class);

    static String map(@Nullable InetAddress source) {

        if (source != null) {
            return source.getHostAddress();
        } else {
            return null;
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "globalDatabase", ignore = true)
    DataBackendDatabaseConnectionEntity toDataBackendGlobalDatabaseDescriptionEntity(DatabaseConnection source);
}
