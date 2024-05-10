/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers.database.connection;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseConnection;
import fr.dademo.supervision.dependencies.entities.DataBackendDescriptionEntity;
import fr.dademo.supervision.dependencies.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.dependencies.entities.database.connection.DataBackendDatabaseConnectionEntity;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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
    @Mapping(source = "backendDescription", target = "backendDescription")
    @Mapping(source = "backendStateExecution", target = "backendStateExecution")
    DataBackendDatabaseConnectionEntity toDataBackendDatabaseConnectionEntity(
        DatabaseConnection source,
        DataBackendDescriptionEntity backendDescription,
        DataBackendStateExecutionEntity backendStateExecution
    );
}
